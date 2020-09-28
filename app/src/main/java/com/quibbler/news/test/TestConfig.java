package com.quibbler.news.test;

import com.quibbler.news.BuildConfig;

public class TestConfig {
    private static final boolean SWITCH = false;
    public static final boolean TEST = BuildConfig.DEBUG && SWITCH;
}
