package com.quibbler.news.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class PreferenceUtil {
    private static final String TAG = "TAG_PreferenceUtil";

    public static final String SHARED_PREFERENCE_NAME = "news_pref";
    public static final String PREFERENCE_KEY_LAST_CACHED_TIME = "pref_last_cached_time";

    private static SharedPreferences sSharedPreference;

    public static void init(Context context) {
        sSharedPreference = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void setValue(@NonNull String key, @NonNull Object value) {
        SharedPreferences.Editor editor = sSharedPreference.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }

    @NonNull
    public static Object getValue(@NonNull String key, @NonNull Object defValue) {
        String type = defValue.getClass().getSimpleName();
        Object result = null;
        switch (type) {
            case "Integer":
                result = sSharedPreference.getInt(key, (Integer) defValue);
                break;
            case "Boolean":
                result = sSharedPreference.getBoolean(key, (Boolean) defValue);
                break;
            case "String":
                result = sSharedPreference.getString(key, (String) defValue);
                break;
            case "Float":
                result = sSharedPreference.getFloat(key, (Float) defValue);
                break;
            case "Long":
                result = sSharedPreference.getLong(key, (Long) defValue);
                break;
        }
        return (result != null) ? result : defValue;
    }

}
