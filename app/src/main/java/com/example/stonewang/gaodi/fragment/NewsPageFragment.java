package com.example.stonewang.gaodi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.stonewang.gaodi.R;

/**
 * Created by Lenovo on 2018/2/5.
 */

public class NewsPageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.tab1, container, false);

        Intent intent = getActivity().getIntent();
        String url = intent.getStringExtra("NewsUrl");
        WebView mWebView = (WebView) v.findViewById(R.id.news_web_view);
        mWebView.getSettings().setJavaScriptEnabled(false);//如果设置为true，则JS广告则会影响阅读体验
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);

        return v;
    }
}

