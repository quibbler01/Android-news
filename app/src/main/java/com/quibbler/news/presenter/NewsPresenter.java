package com.quibbler.news.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.MainThread;

import com.quibbler.news.model.NewsNetWorkModel;
import com.quibbler.news.model.TaskHandler;
import com.quibbler.news.model.bean.NewsDataBean;
import com.quibbler.news.model.database.NewsCacheModel;
import com.quibbler.news.util.Constant;
import com.quibbler.news.util.PreferenceUtil;
import com.quibbler.news.view.callback.NewsCallback;

import java.util.List;

public class NewsPresenter {
    private static final String TAG = "TAG_NewsPresenter";

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private NewsCallback mCallback;
    private NewsNetWorkModel mNewsModel;
    private NewsCacheModel mCachedModel;

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
        mCachedModel = new NewsCacheModel();
    }

    @MainThread
    public void initData() {
        Log.d(TAG, "initData");
        long last = (Long) PreferenceUtil.getValue(Constant.PREFERENCE_KEY_LAST_CACHED_TIME, 0L);
        if (last + NewsCacheModel.CACHED_VALID_TIME < System.currentTimeMillis()) {
            Log.d(TAG, "cache out of time,update from network");
            updateDataFromNetwork();
        } else {
            Log.d(TAG, "cache valid,load from cache");
            updateDataFromDatabase();
        }
    }

    @MainThread
    public void release() {
        Log.d(TAG, "release");
        sInstance = null;
        mNewsModel = null;
        unSubscribeCallback();
    }

    @MainThread
    public void subscribeCallback(NewsCallback callback) {
        mCallback = callback;
    }

    @MainThread
    public void unSubscribeCallback() {
        mCallback = null;
    }

    public void updateDataFromNetwork() {
        TaskHandler.executeUpdateTask(new Runnable() {
            @Override
            public void run() {
                if (null != mNewsModel) {
                    final List<List<NewsDataBean>> result = mNewsModel.updateAllNewsData();
                    NewsCacheModel.cacheNewsToDataBase(result);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onNewsUpdate(result);
                        }
                    });
                }
            }
        });
    }

    public void updateDataFromDatabase() {
        TaskHandler.executeUpdateTask(new Runnable() {
            @Override
            public void run() {
                final List<List<NewsDataBean>> result = NewsCacheModel.getNewsFromDataBase();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onNewsUpdate(result);
                    }
                });
            }
        });
    }

}
