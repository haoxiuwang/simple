
package dev.simplesolution.one;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.view.View;
import android.view.Gravity;
import android.animation.ObjectAnimator;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.graphics.Color;
import android.os.Handler;
import android.webkit.WebChromeClient;
import android.Manifest;
import android.content.pm.PackageManager;



public class MainActivity extends AppCompatActivity {
    private WebServer server;
    private WebView webView;
    private FrameLayout overlay;
    private ValueCallback<Uri[]> mFilePathCallback;
    private static final int FILE_CHOOSER_REQUEST_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
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
        setContentView(R.layout.activity_main);

        // 初始化视图
        webView = findViewById(R.id.webView);
        overlay = findViewById(R.id.overlay);

        // 配置WebView
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

webView.setWebChromeClient(new WebChromeClient() {
    @Override
    public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
        mFilePathCallback = filePathCallback;

        Intent intent = fileChooserParams.createIntent();
        try {
            startActivityForResult(intent, FILE_CHOOSER_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            mFilePathCallback = null;
            return false;
        }
        return true;
    }
});

WebSettings settings = webView.getSettings();
settings.setJavaScriptEnabled(true);
settings.setDomStorageEnabled(true);
settings.setAllowFileAccess(true);
settings.setAllowContentAccess(true);

webView.loadUrl("http://127.0.0.1:8080/");

overlay.setAlpha(0f); // 初始透明

// 渐显动画
overlay.animate()
    .alpha(1f)
    .setDuration(1000) // 1秒渐显
    .withEndAction(new Runnable() {
        @Override
        public void run() {
            // 渐显结束后延迟 2 秒执行渐隐
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    overlay.animate()
                        .alpha(0f)
                        .setDuration(1000) // 1秒渐隐
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                overlay.setVisibility(View.GONE); // 隐藏视图
                            }
                        })
                        .start();
                }
            }, 2000); // 停留 2 秒后再渐隐
        }
    })
    .start();


    } catch (Exception e) {
        e.printStackTrace();
        Toast.makeText(this, "WebView 初始化失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}   

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
        if (mFilePathCallback != null) {
            Uri[] results = null;
            // 如果操作成功并有返回数据
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{ Uri.parse(dataString) };
                    }
                }
            }
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        }
    } else {
        super.onActivityResult(requestCode, resultCode, data);
    }
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
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack(); // 如果WebView有历史记录，则后退
        } else {
            super.onBackPressed(); // 否则执行系统默认行为
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
