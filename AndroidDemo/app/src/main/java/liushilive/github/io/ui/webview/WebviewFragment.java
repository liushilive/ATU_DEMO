package liushilive.github.io.ui.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import liushilive.github.io.R;
import liushilive.github.io.view.WebViewBase;

public class WebviewFragment extends WebViewBase {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_webview, container, false);

        init(root);

        mWebView.loadUrl("https://liushilive.gitee.io/html_example/index1.html");
        return root;
    }

}