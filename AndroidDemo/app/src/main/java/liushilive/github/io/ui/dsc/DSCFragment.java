package liushilive.github.io.ui.dsc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import liushilive.github.io.MainActivity;
import liushilive.github.io.R;
import liushilive.github.io.view.WebViewBase;

public class DSCFragment extends WebViewBase {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dsc, container, false);

        init(root);
//        mWebView.loadUrl("http://192.168.2.253/dsc");
        if (MainActivity.url.equals("")) mWebView.loadUrl("");
        else mWebView.loadUrl("http://" + MainActivity.url + "/dsc");
        return root;
    }

}