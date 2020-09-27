package com.quibbler.news.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quibbler.news.R;
import com.quibbler.news.model.bean.NewsDataBean;
import com.quibbler.news.test.TestConfig;
import com.quibbler.news.test.TestData;
import com.quibbler.news.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class NewsTopicViewpagerAdapter extends RecyclerView.Adapter<NewsTopicViewpagerAdapter.ViewHolder> {
    private static final String TAG = "TAG_NewsTopicViewpagerAdapter";

    private Context mContext;
    private List<List<NewsDataBean>> mNewsData = new ArrayList<>(Constant.TOPICS_POSITION.length);

    public NewsTopicViewpagerAdapter(Context context) {
        this.mContext = context;
        for (int pos : Constant.TOPICS_POSITION) {
            mNewsData.add(new ArrayList<NewsDataBean>());
            if (TestConfig.TEST) {
                List<NewsDataBean> dataBeans = new ArrayList<>();
                for (int i = 0; i < 10; ++i) {
                    dataBeans.add(new NewsDataBean(TestData.title));
                }
                mNewsData.set(pos, dataBeans);
            }
        }
    }

    public NewsTopicViewpagerAdapter(Context context, List<List<NewsDataBean>> newsData) {
        this.mContext = context;
        this.mNewsData = newsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newsContainer = LayoutInflater.from(mContext).inflate(R.layout.news_topic_viewpager2_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(newsContainer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.mRecyclerView.setLayoutManager(layoutManager);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mAdapter.updateData(mNewsData.get(position));
        holder.mRecyclerView.setAdapter(holder.mAdapter);
        holder.mAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, " " + mNewsData.size());
        return mNewsData.size();
    }

    public void updateTopic(int typePosition, List<NewsDataBean> newsData) {
        mNewsData.set(typePosition, newsData);
        notifyItemChanged(typePosition);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        NewsRecyclerViewAdapter mAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.news_recyclerview);
            mAdapter = new NewsRecyclerViewAdapter();
        }
    }

}
