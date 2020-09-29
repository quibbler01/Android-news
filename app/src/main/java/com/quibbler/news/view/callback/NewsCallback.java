package com.quibbler.news.view.callback;

import androidx.annotation.MainThread;

import com.quibbler.news.model.bean.NewsDataBean;

import java.util.List;

public interface NewsCallback {

    @MainThread
    void onNewsTopicUpdate(int type, List<NewsDataBean> dataBeans);

    void onNewsUpdate(List<List<NewsDataBean>> dataBeans);

}
