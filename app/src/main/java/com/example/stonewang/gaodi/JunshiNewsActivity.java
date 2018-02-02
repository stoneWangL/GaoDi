package com.example.stonewang.gaodi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by stoneWang on 2017/3/17.
 */

public class JunshiNewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent intent = getIntent();
        String url = intent.getStringExtra("NewsUrl");

        WebView mWebView = (WebView) findViewById(R.id.news_web_view);
        mWebView.getSettings().setJavaScriptEnabled(false);//如果设置为true，则JS广告则会影响阅读体验
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
    }
}
