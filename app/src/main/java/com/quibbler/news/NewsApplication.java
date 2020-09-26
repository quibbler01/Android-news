package com.quibbler.news;

import android.app.Application;
import android.content.Context;

public class NewsApplication extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static Context getApplication() {
        return sApplication;
    }
}
