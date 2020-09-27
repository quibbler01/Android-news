package com.quibbler.news.model;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

import com.google.gson.Gson;
import com.quibbler.news.BuildConfig;
import com.quibbler.news.NewsApplication;
import com.quibbler.news.model.bean.NewsDataBean;
import com.quibbler.news.model.bean.RequestBean;
import com.quibbler.news.util.Constant;
import com.quibbler.news.util.NetWorkUtil;
import com.quibbler.news.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class NewsNetWorkModel {
    private static final String TAG = "TAG_NetWorkPresenter";

    private String mKey;

    @MainThread
    public NewsNetWorkModel() {
        mKey = Utils.getMetaData(Constant.KEYNAME, NewsApplication.getApplication());
    }

    /**
     * update all type news
     */
    @WorkerThread
    public void updateAllNewsData() {
        updateNewsData(Constant.TOPICS_TYPE);
    }

    /**
     * @param types news type list
     */
    @WorkerThread
    public void updateNewsData(String[] types) {
        for (String type : types) {
            updateNewsData(type);
        }
    }

    /**
     * @param type news type
     */
    @WorkerThread
    public List<NewsDataBean> updateNewsData(final String type) {
        List<NewsDataBean> news = new ArrayList<>();
        String url = NetWorkUtil.buildUrl(type, mKey);
        if (BuildConfig.DEBUG) {
            Log.w(TAG, "news type: " + type + " url " + url);
        }
        String result = NetWorkUtil.requestFromNetWork(url);
        try {
            Gson gson = new Gson();
            RequestBean requestBean = gson.fromJson(result, RequestBean.class);
            news = requestBean.getResult().getData();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        Log.d(TAG, "news type: " + type + " result size " + news.size());
        return news;
    }

}