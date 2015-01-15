package me.doapps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import me.doapps.utils.Util_Web;
import me.doapps.youapp.R;
import me.doapps.youapp.YouApp;

/**
 * Created by Gantz on 8/01/15.
 */
public class Fragment_Web extends Fragment {

    private WebView webView;

    public static final Fragment_Web newInstance(){
        return new Fragment_Web();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EasyTracker easyTracker = EasyTracker.getInstance(getActivity());
        easyTracker.send(MapBuilder
                .createEvent("WEB",
                        "CREANDO",
                        "SE CREO LA WEBVIEW",
                        null)
                .build());

        webView = (WebView) getView().findViewById(R.id.web);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(((YouApp)getActivity()).getUrl());
    }
}
