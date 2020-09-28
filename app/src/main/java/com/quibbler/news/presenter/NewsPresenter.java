package com.quibbler.news.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.quibbler.news.model.NewsNetWorkModel;
import com.quibbler.news.model.TaskHandler;
import com.quibbler.news.model.bean.NewsDataBean;
import com.quibbler.news.view.callback.NewsCallback;

import java.util.List;

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

    public void initData() {
        Log.d(TAG, "initData");
        updateDataFromNetwork();
    }

    public void release() {
        Log.d(TAG, "release");
        sInstance = null;
        mNewsModel = null;
        unSubscribeCallback();
    }

    public void subscribeCallback(NewsCallback callback) {
        mCallback = callback;
    }

    public void unSubscribeCallback() {
        mCallback = null;
    }

    public void updateDataFromNetwork() {
        TaskHandler.executeUpdateTask(new Runnable() {
            @Override
            public void run() {
                if (null != mNewsModel) {
                    final List<NewsDataBean> data = mNewsModel.updateNewsData("top");
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onNewsTopicUpdate(0, data);
                        }
                    });
                }
            }
        });
    }

}
