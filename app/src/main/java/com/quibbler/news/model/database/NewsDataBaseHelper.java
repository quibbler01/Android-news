package com.quibbler.news.model.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.quibbler.news.NewsApplication;

public class NewsDataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "TAG_NewsDataBaseHelper";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "news.db";

    //news table
    public static final String DB_TABLE = "news";
    //news table column name
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_UNIQUE_KEY = "uniquekey";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_AUTHOR = "author_name";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_THUMBNAIL_PICTURE_URL_1 = "thumbnail_pic_s";
    public static final String COLUMN_THUMBNAIL_PICTURE_URL_2 = "thumbnail_pic_s02";
    public static final String COLUMN_THUMBNAIL_PICTURE_URL_3 = "thumbnail_pic_s03";

    //sql to create news table
    private static final String CREATE_TABLE = "create table if not exists " + DB_TABLE
            + "(uniquekey text ,type text, title text, date text, category text, author_name text, url text, thumbnail_pic_s text, thumbnail_pic_s02 text, thumbnail_pic_s03 text)";
    //sql to drop news table
    private static final String DROP_TABLE = "drop table if exists " + DB_TABLE;

    //Single instance of Database helper
    private static NewsDataBaseHelper sInstance = null;

    public static NewsDataBaseHelper getsInstance() {
        if (null == sInstance) {
            synchronized (NewsDataBaseHelper.class) {
                if (null == sInstance) {
                    sInstance = new NewsDataBaseHelper(NewsApplication.getApplication());
                }
            }
        }
        return sInstance;
    }

    private NewsDataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing to do now.
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onDowngrade from " + oldVersion + " to " + newVersion);
        if (newVersion < oldVersion) {
            db.execSQL(DROP_TABLE);
        }
        onCreate(db);
    }
}
