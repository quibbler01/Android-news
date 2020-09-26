package com.quibbler.news.util;

public class Constant {

    /**
     * 接口地址：http://v.juhe.cn/toutiao/index
     * 返回格式：json
     * 请求方式：get/post
     * 请求示例：http://v.juhe.cn/toutiao/index?type=top&key=9e7168cce16f7abca0a36c33657b1cc9
     */
    public static final String URL = "http://v.juhe.cn/toutiao/index?type=%s&key=%s";

    public static final String[] TOPICS = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};

}
