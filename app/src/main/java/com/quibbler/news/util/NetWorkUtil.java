package com.quibbler.news.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.quibbler.news.BuildConfig;
import com.quibbler.news.NewsApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class NetWorkUtil {
    private static final String TAG = "TAG_NetWorkUtil";

    /**
     * default top topic of new
     *
     * @param key news api key
     * @return request url
     */
    @NonNull
    public static String buildUrl(@NonNull String key) {
        return String.format(Locale.getDefault(), Constant.URL, Constant.TOPICS_STRING[0], key);
    }

    /**
     * build request url with specific new newsType
     *
     * @param newsType new topic newsType, see {@link Constant#TOPICS_STRING}
     * @param key      news api key
     * @return request url
     */
    @NonNull
    public static String buildUrl(@Nullable String newsType, @NonNull String key) {
        if (TextUtils.isEmpty(newsType)) {
            return buildUrl(key);
        } else {
            return String.format(Locale.getDefault(), Constant.URL, newsType, key);
        }
    }

    @WorkerThread
    @Nullable
    public static String requestFromNetWork(@Nullable String url) {
        if (TextUtils.isEmpty(url)) {
            Log.d(TAG, " request with empty url ?!");
            return null;
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "requestFromNetWork " + url);
        }
        InputStream inputStream = null;
        BufferedReader reader = null;
        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL requestUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);
            httpURLConnection.setDoInput(true);
            inputStream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            Utils.close(inputStream);
            Utils.close(reader);
            Utils.disConnected(httpURLConnection);
        }
        return null;
    }

    /**
     * @return is network available
     */
    public static boolean isNetWorkAvailable() {
        Context context = NewsApplication.getApplication();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (null != networkInfo) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    public static int getNetWorkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        byte netType;
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                case 0:
                    switch (ni.getSubtype()) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                        case 16:
                            netType = 2;
                            Log.d(TAG, "切换到2G环境下");
                            return netType;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                        case 17:
                            netType = 3;
                            Log.d(TAG, "切换到3G环境下");
                            return netType;
                        case 13:
                        case 18:
                            netType = 4;
                            Log.d(TAG, "切换到4G环境下");
                            return netType;
                        default:
                            String subtypeName = ni.getSubtypeName();
                            if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA") && !subtypeName.equalsIgnoreCase("CDMA2000")) {
                                netType = 5;
                            } else {
                                netType = 3;
                            }

                            Log.d(TAG, "未知网络");
                            return netType;
                    }
                case 1:
                    netType = 1;
                    Log.d(TAG, "切换到wifi环境下");
                    break;
                default:
                    netType = 5;
                    Log.d(TAG, "未知网络");
            }
        } else {
            netType = -1;
            Log.d(TAG, "当前无网络连接");
        }

        return netType;
    }

}
