package com.quibbler.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.material.tabs.TabLayout;
import com.quibbler.news.model.bean.NewsDataBean;
import com.quibbler.news.presenter.NewsPresenter;
import com.quibbler.news.util.Constant;
import com.quibbler.news.view.adapter.NewsTopicViewpagerAdapter;
import com.quibbler.news.view.callback.NewsCallback;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsCallback {
    private static final String TAG = "TAG_NewsActivity";

    private NewsPresenter mPresenter;

    //View
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private NewsTopicViewpagerAdapter mViewPager2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPresenter();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        releasePresenter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        Log.d(TAG, "initPresenter");
        mPresenter = NewsPresenter.getNewsPresenter();
        mPresenter.subscribeCallback(this);
        mPresenter.initData();
    }

    private void releasePresenter() {
        Log.d(TAG, "releasePresenter");
        mPresenter.release();
        mPresenter = null;
    }

    @Override
    public void onNewsTopicUpdate(@NonNull int type, @NonNull List<NewsDataBean> dataBeans) {
        Log.d(TAG, "Callback onNewsTopicUpdate " + dataBeans.size());
        mViewPager2Adapter.updateTopic(type, dataBeans);
    }

    @Override
    public void onNewsUpdate(List<List<NewsDataBean>> dataBeans) {
        mViewPager2Adapter.updateNews(dataBeans);
    }
}