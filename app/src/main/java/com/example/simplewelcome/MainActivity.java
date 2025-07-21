
package com.example.simplewelcome;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private Webserver server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        server = new WebServer(this, 8080);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
        }
    }
}
