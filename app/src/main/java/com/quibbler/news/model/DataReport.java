package com.quibbler.news.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.quibbler.news.NewsApplication;
import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * Buried point reporting tool, using UMeng SDK
 */
public class DataReport {
    private static final String TAG = "TAG_DataReport";

    public static final String NEWS_ITEM_CLICK = "news_item_click";
    public static final String NEWS_ITEM_CLICK_TIME = "time";
    public static final String NEWS_ITEM_CLICK_TITLE = "title";
    public static final String NEWS_ITEM_CLICK_URL = "url";

    public static final String NEWS_DETAIL_CLICK_BACK = "news_detail_click_back";

    /**
     * report view click event
     *
     * @param eventKey click event key
     */
    public static void reportOnClickEvent(@NonNull String eventKey) {
        Log.d(TAG, "eventKey");
        MobclickAgent.onEvent(NewsApplication.getApplication(), eventKey);
    }

    /**
     * report view click event with Map values
     *
     * @param eventKey click event key
     * @param values   parameters carried in the buy order report,using {@link Map<String,String>}
     */
    public static void reportOnClickEvent(@NonNull String eventKey, @NonNull Map<String, String> values) {
        Log.d(TAG, "eventKey with Map");
        MobclickAgent.onEvent(NewsApplication.getApplication(), eventKey, values);
    }

}
