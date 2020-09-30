package com.quibbler.news.model.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.quibbler.news.model.bean.NewsDataBean;
import com.quibbler.news.util.Constant;
import com.quibbler.news.util.PreferenceUtil;
import com.quibbler.news.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class NewsCacheModel {
    private static final String TAG = "TAG_NewsCacheModel";

    public static final long CACHED_VALID_TIME = 60 * 60 * 1000L;

    @WorkerThread
    public static void cacheNewsToDataBase(List<List<NewsDataBean>> result) {
        long start = System.currentTimeMillis();
        for (int pos = 0; pos < result.size(); ++pos) {
            cacheNewsToDataBase(result.get(pos), Constant.TOPICS_TYPE[pos]);
        }
        long end = System.currentTimeMillis();
        PreferenceUtil.setValue(PreferenceUtil.PREFERENCE_KEY_LAST_CACHED_TIME, System.currentTimeMillis());
        Log.i(TAG, "cache all News To DataBase cost time : " + (end - start));
    }

    @WorkerThread
    public static void cacheNewsToDataBase(final List<NewsDataBean> newsDataBeans, @NonNull final String type) {
        long start = System.currentTimeMillis();
        NewsDataBaseHelper newsDataBaseHelper = NewsDataBaseHelper.getsInstance();
        SQLiteDatabase database = newsDataBaseHelper.getWritableDatabase();
        database.beginTransaction();
        for (NewsDataBean dataBean : newsDataBeans) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NewsDataBaseHelper.COLUMN_TYPE, type);
            contentValues.put(NewsDataBaseHelper.COLUMN_UNIQUE_KEY, dataBean.getUniquekey());
            contentValues.put(NewsDataBaseHelper.COLUMN_TITLE, dataBean.getTitle());
            contentValues.put(NewsDataBaseHelper.COLUMN_DATE, dataBean.getDate());
            contentValues.put(NewsDataBaseHelper.COLUMN_CATEGORY, dataBean.getCategory());
            contentValues.put(NewsDataBaseHelper.COLUMN_AUTHOR, dataBean.getAuthor_name());
            contentValues.put(NewsDataBaseHelper.COLUMN_URL, dataBean.getUrl());
            contentValues.put(NewsDataBaseHelper.COLUMN_THUMBNAIL_PICTURE_URL_1, dataBean.getThumbnail_pic_s());
            contentValues.put(NewsDataBaseHelper.COLUMN_THUMBNAIL_PICTURE_URL_2, dataBean.getThumbnail_pic_s02());
            contentValues.put(NewsDataBaseHelper.COLUMN_THUMBNAIL_PICTURE_URL_3, dataBean.getThumbnail_pic_s03());
            database.insert(NewsDataBaseHelper.DB_TABLE, null, contentValues);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        long end = System.currentTimeMillis();
        Log.i(TAG, "cache type : " + type + " News To DataBase cost time : " + (end - start));
    }

    @WorkerThread
    @NonNull
    public static List<List<NewsDataBean>> getNewsFromDataBase() {
        long start = System.currentTimeMillis();
        List<List<NewsDataBean>> result = new ArrayList<>();
        for (String type : Constant.TOPICS_TYPE) {
            result.add(getNewsFromDataBase(type));
        }
        long end = System.currentTimeMillis();
        Log.i(TAG, "get all News from DataBase cost time : " + (end - start) + "\tresult size " + result.size());
        return result;
    }


    @WorkerThread
    @NonNull
    public static List<NewsDataBean> getNewsFromDataBase(String type) {
        long start = System.currentTimeMillis();
        List<NewsDataBean> result = new ArrayList<>();
        NewsDataBaseHelper newsDataBaseHelper = NewsDataBaseHelper.getsInstance();
        SQLiteDatabase database = newsDataBaseHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(NewsDataBaseHelper.DB_TABLE, null, NewsDataBaseHelper.COLUMN_TYPE + " = ? ", new String[]{type}, null, null, NewsDataBaseHelper.COLUMN_DATE + " desc ");
            Log.d(TAG, "(null != cursor) = " + (null != cursor));
            if (null != cursor && cursor.moveToFirst()) {
                Log.d(TAG, "cursor count = " + cursor.getCount());
                while (cursor.moveToNext()) {
                    String uniquekey = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_UNIQUE_KEY));
                    String title = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_TITLE));
                    String date = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_DATE));
                    String category = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_CATEGORY));
                    String author_name = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_AUTHOR));
                    String url = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_URL));
                    String thumbnail_pic_s = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_THUMBNAIL_PICTURE_URL_1));
                    String thumbnail_pic_s02 = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_THUMBNAIL_PICTURE_URL_2));
                    String thumbnail_pic_s03 = cursor.getString(cursor.getColumnIndex(NewsDataBaseHelper.COLUMN_THUMBNAIL_PICTURE_URL_3));
                    NewsDataBean newsDataBean = new NewsDataBean(uniquekey, title, date, category, author_name, url, thumbnail_pic_s, thumbnail_pic_s02, thumbnail_pic_s03);
                    result.add(newsDataBean);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            Utils.close(cursor);
        }
        long end = System.currentTimeMillis();
        Log.i(TAG, "get " + type + " News From DataBase cost " + (end - start) + "\tresult size " + result.size());
        return result;
    }

}
