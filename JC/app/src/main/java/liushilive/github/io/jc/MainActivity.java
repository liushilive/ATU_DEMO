package liushilive.github.io.jc;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 定义文件夹目录地址
        final String path = "/sdcard/Android/media/JC/html";
        // 创建文件目录
        File destDir = new File(path);
        if (!destDir.exists()) {
            if (!destDir.mkdirs()) {
                throw new RuntimeException("创建文件目录失败！");
            }
        }

        final WebView webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webview.setWebChromeClient(new WebChromeClient() {
        });
        webview.setWebViewClient(new WebViewClient() {
        });

        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //按返回键操作并且能回退网页
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
                        //后退
                        webview.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        webview.loadUrl("file://" + path + File.separator + "index.html");
    }

}