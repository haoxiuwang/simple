// 文件路径：app/src/main/java/whx/apps/youtube/LocalWebServer.java
// 文件名必须与类名一致
package dev.simplesolution.one;

import android.content.Context;
import fi.iki.elonen.NanoHTTPD;
import java.io.InputStream;

class WebServer extends NanoHTTPD {
    private final Context context;

    public WebServer(Context context, int port) {
        super(port);
        this.context = context;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        if (uri.equals("/")) uri = "/index.html";

        try {
            InputStream input = context.getAssets().open("www" + uri);
            String mime = getMimeType(uri);
            return newFixedLengthResponse(Response.Status.OK, mime, input, input.available());
        } catch (Exception e) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found: " + uri);
        }
    }

    private String getMimeType(String uri) {
        if (uri.endsWith(".html")) return "text/html";
        if (uri.endsWith(".js")) return "application/javascript";
        if (uri.endsWith(".css")) return "text/css";
        if (uri.endsWith(".png")) return "image/png";
        if (uri.endsWith(".jpg") || uri.endsWith(".jpeg")) return "image/jpeg";
        if (uri.endsWith(".json")) return "application/json";
        return "text/plain";
    }
}
