package com.quibbler.news;

import android.app.Application;
import android.content.Context;

import com.quibbler.news.util.PreferenceUtil;
import com.quibbler.news.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class NewsApplication extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        PreferenceUtil.init(this);
        UMConfigure.init(this, "5f74162280455950e49c913e", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        UMConfigure.setLogEnabled(true);
    }

    public static Context getApplication() {
        return sApplication;
    }
}
