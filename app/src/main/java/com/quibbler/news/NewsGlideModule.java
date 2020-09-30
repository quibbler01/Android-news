package com.quibbler.news;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class NewsGlideModule extends AppGlideModule {
    //Maximum disk cache
    private static final int MAX_CACHED_SIZE = 128 * 1024 * 1024;
    //cache directory : /data/user/0/com.quibbler.news/cache
    private static final String CACHED_DIRECTORY = "/image";

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        DiskLruCacheFactory diskLruCacheFactory = new DiskLruCacheFactory(context.getCacheDir().getAbsolutePath() + CACHED_DIRECTORY, MAX_CACHED_SIZE);
        builder.setDiskCache(diskLruCacheFactory);
    }

}
