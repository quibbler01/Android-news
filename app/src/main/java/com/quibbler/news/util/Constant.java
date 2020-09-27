package com.quibbler.news.util;

import com.quibbler.news.R;

public class Constant {

    public static final String KEYNAME = "key";

    /**
     * 接口地址：http://v.juhe.cn/toutiao/index
     * 返回格式：json
     * 请求方式：get/post
     * 请求示例：http://v.juhe.cn/toutiao/index?type=top&key=9e7168cce16f7abca0a36c33657b1cc9
     */
    public static final String URL = "http://v.juhe.cn/toutiao/index?type=%s&key=%s";
    public static final String[] TOPICS_TYPE = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};

    public static final int[] TOPICS_STRING = {R.string.topic_popular, R.string.topic_society, R.string.topic_domestic,
            R.string.topic_international, R.string.topic_entertainment, R.string.topic_physical_education,
            R.string.topic_military, R.string.topic_technology, R.string.topic_finance, R.string.topic_fashion};
    public static final int[] TOPICS_POSITION = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

}
