package com.erginus.blendedd.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.erginus.blendedd.R;


public class WebViewFragment extends Fragment {


    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        String url= getArguments().getString("url");
        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        webView.loadData(url, "text/html","UTF-8");
        return rootView;
    }

}
