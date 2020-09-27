package com.quibbler.news.util;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

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
            Log.d(TAG, e.toString());
        } finally {
            Utils.close(inputStream);
            Utils.close(reader);
            Utils.disConnected(httpURLConnection);
        }
        return null;
    }

}
