package liushilive.github.io.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import liushilive.github.io.MainActivityModel;
import liushilive.github.io.R;

public class WebViewBase extends Fragment {
    protected WebView mWebView;
    protected MainActivityModel mainActivityModel;

    protected void init(View root) {
        mainActivityModel = new ViewModelProvider(requireActivity()).get(MainActivityModel.class);
        mainActivityModel.setToolbar("玩命加载中。。。");

        mWebView = root.findViewById(R.id.webview);
        WebSettings mWebSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("UTF-8");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setGeolocationEnabled(true);

        //设置不用系统浏览器打开,直接显示在当前WebView
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                Log.d("webView", "onFormResubmission：" + resend.toString());
                super.onFormResubmission(view, dontResend, resend);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request) {
                Log.d("webView", "shouldOverrideUrlLoading：" + request.getUrl().toString());

                if (request.getUrl().getScheme().equals("http") || request.getUrl().getScheme().equals("https")) {
                    return super.shouldOverrideUrlLoading(view, request);
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e("webView", "onReceivedSslError：" + error.toString());
                handler.proceed();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d("webView", "onLoadResource：" + url);
                super.onLoadResource(view, url);
            }

            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("webView", "onPageStarted：" + url);
            }


            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("webView", "onPageFinished：" + url);
            }
        });

        //设置WebChromeClient类
        mWebView.setWebChromeClient(new WebChromeClient() {
            // 获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String t) {
                Log.d("webView", "onReceivedTitle：" + t);
                super.onReceivedTitle(view, t);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    mWebView.evaluateJavascript("window.localStorage.setItem('localShow','false');",
                            null);
                } else {
                    mWebView.loadUrl("window.localStorage.setItem('localShow','false');");
                }
                mainActivityModel.setToolbar(t);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin,
                                                           final GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
//
//            @Override
//            public void onReachedMaxAppCacheSize(long spaceNeeded,
//                                                 long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
//                quotaUpdater.updateQuota(spaceNeeded * 2);
//            }
        });

        //处理下载事件
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //这里处理返回键事件
                        if (mWebView.canGoBack()) {
                            mWebView.goBack();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清除记录
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.clearFormData();
        mWebView.destroy();
    }
}