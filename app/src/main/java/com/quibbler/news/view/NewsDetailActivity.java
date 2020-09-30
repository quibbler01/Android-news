package com.quibbler.news.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quibbler.news.R;
import com.quibbler.news.model.DataReport;

public class NewsDetailActivity extends AppCompatActivity {
    private static final String TAG = "TAG_NewsDetailActivity";

    public static final String NEWS_TITLE = "news_title";
    public static final int NEWS_TITLE_LENGTH = 18;
    public static final String NEWS_URL = "news_url";

    private WebView mWebView;
    ActionBar mActionBar;

    private String mNewsTitle;
    private String mNewsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        mNewsUrl = intent.getStringExtra(NEWS_URL);
        mNewsTitle = intent.getStringExtra(NEWS_TITLE);
        Log.d(TAG, "mNewsUrl : " + mNewsTitle + "\tmNewsUrl : " + mNewsUrl);
        if (null != mActionBar) {
            mActionBar.setTitle(mNewsTitle);
        }
        mWebView.loadUrl(mNewsUrl);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void initView() {
        mActionBar = getSupportActionBar();
        if (null != mActionBar) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mWebView = findViewById(R.id.news_web_view);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mNewsUrl = intent.getStringExtra(NEWS_URL);
        if (null != mNewsUrl) {
            mWebView.loadUrl(mNewsUrl);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            DataReport.reportOnClickEvent(DataReport.NEWS_DETAIL_CLICK_BACK);
            finish();
            return true;
        }
        return false;
    }
}