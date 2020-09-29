package com.quibbler.news.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quibbler.news.NewsApplication;

import java.io.Closeable;
import java.net.HttpURLConnection;

public class Utils {
    private static final String TAG = "TAG_Utils";

    /**
     * get mete data values,see {Android meta-data用法 <>http://quibbler.cn/?thread-466.htm</>}
     *
     * @param key     mete-data key name
     * @param context Application Context {@link NewsApplication#getApplication()}
     * @return the key store by mete-data
     */
    @Nullable
    public static String getMetaData(@Nullable String key, @NonNull Context context) {
        String value = "";
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = ai.metaData.getString(key);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return value;
    }

    public static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void disConnected(HttpURLConnection connection) {
        if (null != connection) {
            try {
                connection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

}
