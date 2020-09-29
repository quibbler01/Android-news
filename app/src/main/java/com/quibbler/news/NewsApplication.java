package com.quibbler.news;

import android.app.Application;
import android.content.Context;

import com.quibbler.news.util.PreferenceUtil;

public class NewsApplication extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        PreferenceUtil.init(this);
    }

    public static Context getApplication() {
        return sApplication;
    }
}
