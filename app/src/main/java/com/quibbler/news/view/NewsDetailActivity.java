package com.quibbler.news.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.quibbler.news.R;
import com.quibbler.news.model.DataReport;
import com.quibbler.news.model.NetWorkChangeReceiver;
import com.quibbler.news.util.NetWorkUtil;
import com.quibbler.news.view.callback.Receiver;

public class NewsDetailActivity extends AppCompatActivity implements Receiver {
    private static final String TAG = "NewsDetailActivity";

    public static final String NEWS_TITLE = "news_title";
    public static final int NEWS_TITLE_LENGTH = 18;
    public static final String NEWS_URL = "news_url";

    private WebView mWebView;
    private ActionBar mActionBar;
    private BroadcastReceiver mNetworkReceiver;

    private boolean isLoadSuccess = false;
    private String mNewsTitle;
    private String mNewsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mNetworkReceiver = new NetWorkChangeReceiver(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart ---> registerReceiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NetWorkChangeReceiver.CONNECTIVITY_CHANGE);
        registerReceiver(mNetworkReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ---> unregisterReceiver");
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkReceiver = null;
        mWebView.clearCache(true);
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
        if (!isLoadSuccess && NetWorkUtil.isNetWorkAvailable()) {
            mWebView.reload();
        }
    }

    private void initView() {
        mActionBar = getSupportActionBar();
        if (null != mActionBar) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mWebView = findViewById(R.id.news_web_view);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (100 == newProgress) {
                    isLoadSuccess = true;
                }
            }
        });
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        Intent intent = getIntent();
        mNewsUrl = intent.getStringExtra(NEWS_URL);
        mNewsTitle = intent.getStringExtra(NEWS_TITLE);
        Log.d(TAG, "mNewsUrl : " + mNewsTitle + "\tmNewsUrl : " + mNewsUrl);
        if (null != mActionBar) {
            mActionBar.setTitle(mNewsTitle);
        }
        if (NetWorkUtil.isNetWorkAvailable()) {
            mWebView.loadUrl(mNewsUrl);
        }
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

    @Override
    public void onReceive() {
        Log.d(TAG, "onReceive " + isLoadSuccess);
        if (!isLoadSuccess) {
            if (NetWorkUtil.isNetWorkAvailable()) {
                mWebView.loadUrl(mNewsUrl);
            }
        }
    }
}