
package dev.simplesolution.one;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.view.View;
public class MainActivity extends AppCompatActivity {
    private WebServer server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        server = new WebServer(this, 8080);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
// 沉浸式全屏，隐藏状态栏和导航栏
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION     // hide nav bar
            | View.SYSTEM_UI_FLAG_FULLSCREEN          // hide status bar
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


try {
        WebView webView = new WebView(this);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("加载失败")
                        .setMessage("无法加载页面：\n" + description)
                        .setPositiveButton("确定", null)
                        .show();

                view.loadData("<h1>页面加载失败</h1><p>请检查服务器是否运行。</p>", "text/html", "UTF-8");
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        // 加载本地网页
        webView.loadUrl("http://127.0.0.1:8080/");

        // 设置视图
        setContentView(webView);
    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "WebView 初始化失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
        // setContentView(R.layout.activity_main);
    }



    @Override
public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
        }
    }
}
