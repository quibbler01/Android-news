package com.quibbler.news.presenter;

import android.os.Handler;
import android.os.Looper;

import com.quibbler.news.model.NewsNetWorkModel;
import com.quibbler.news.model.TaskHandler;
import com.quibbler.news.view.callback.NewsCallback;

public class NewsPresenter {
    private static final String TAG = "TAG_NewsPresenter";

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private NewsCallback mCallback;
    private NewsNetWorkModel mNewsModel;

    private static NewsPresenter sInstance;

    public static NewsPresenter getNewsPresenter() {
        if (null == sInstance) {
            synchronized (NewsPresenter.class) {
                if (null == sInstance) {
                    sInstance = new NewsPresenter();
                }
            }
        }
        return sInstance;
    }

    private NewsPresenter() {
        mNewsModel = new NewsNetWorkModel();
    }

    public void subscribeCallback(NewsCallback callback) {
        mCallback = callback;
    }

    public void unSubscribeCallback() {
        mCallback = null;
    }

    public void release() {
        mNewsModel = null;
        unSubscribeCallback();
    }

    public void initData() {
        updateDataFromNetwork();
    }

    public void updateDataFromNetwork() {
        TaskHandler.executeUpdateTask(new Runnable() {
            @Override
            public void run() {
                if (null != mNewsModel) {
                    mNewsModel.updateAllNewsData();
                }
            }
        });
    }

}
