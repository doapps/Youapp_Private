package me.doapps.utils;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Util_Web extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return (false);
    }
}