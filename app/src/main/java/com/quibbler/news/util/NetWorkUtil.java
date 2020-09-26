package com.quibbler.news.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

public class NetWorkUtil {

    /**
     * default top topic of new
     *
     * @param key news api key
     * @return request url
     */
    @NonNull
    public static String buildUrl(@NonNull String key) {
        return String.format(Locale.getDefault(), Constant.URL, Constant.TOPICS[0], key);
    }

    /**
     * build request url with specific new newsType
     *
     * @param newsType new topic newsType
     * @param key  news api key
     * @return request url
     */
    @NonNull
    public static String buildUrl(@Nullable String newsType, @NonNull String key) {
        if (newsType == null || newsType.length() == 0) {
            return buildUrl(key);
        } else {
            return String.format(Locale.getDefault(), Constant.URL, newsType, key);
        }
    }

}
