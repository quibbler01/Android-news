package com.quibbler.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.quibbler.news.model.bean.NewsDataBean;
import com.quibbler.news.presenter.NewsPresenter;
import com.quibbler.news.util.Constant;
import com.quibbler.news.view.adapter.NewsTopicViewpagerAdapter;
import com.quibbler.news.view.callback.NewsCallback;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsCallback {
    private NewsPresenter mPresenter;

    //View
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private NewsTopicViewpagerAdapter mViewPager2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePresenter();
    }

    private void initView() {
        mTabLayout = findViewById(R.id.news_title_tablayout);
        mViewPager2 = findViewById(R.id.news_content_viewpager2);
        mViewPager2.setOffscreenPageLimit(2);
        mViewPager2Adapter = new NewsTopicViewpagerAdapter(this);
        mViewPager2.setAdapter(mViewPager2Adapter);
        linkTabWithPager();
    }

    private void linkTabWithPager() {
        for (int title : Constant.TOPICS_STRING) {
            mTabLayout.addTab(mTabLayout.newTab().setText(title));
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
        mTabLayout.selectTab(mTabLayout.getTabAt(0));
    }

    private void initPresenter() {
        mPresenter = NewsPresenter.getNewsPresenter();
        mPresenter.subscribeCallback(this);
//        mPresenter.initData();
    }

    private void releasePresenter() {
        mPresenter.release();
        mPresenter = null;
    }

    @Override
    public void onNewsTopicUpdate(int type, List<NewsDataBean> dataBeans) {
        mViewPager2Adapter.updateTopic(type, dataBeans);
    }
}