package com.quibbler.news.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quibbler.news.NewsApplication;
import com.quibbler.news.R;
import com.quibbler.news.model.bean.NewsDataBean;

import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "TAG_NewsRecyclerViewAdapter";

    private Context mContext;
    private int mNewsType = 0;
    private List<NewsDataBean> mNewsData = new ArrayList<>();

    public NewsRecyclerViewAdapter() {
        this.mContext = NewsApplication.getApplication();
    }

    public NewsRecyclerViewAdapter(Context mContext, List<NewsDataBean> mNewsData) {
        this.mContext = mContext;
        this.mNewsData = mNewsData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewsDataBean newsData = mNewsData.get(position);
        Log.d(TAG, "" + newsData.getTitle());
        holder.title.setText(newsData.getTitle());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount " + mNewsData.size());
        return mNewsData.size();
    }

    public void updateData(@NonNull List<NewsDataBean> newsData) {
        mNewsData.clear();
        mNewsData.addAll(newsData);
        Log.d(TAG, "update " + mNewsData.size());
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView newsImageOne;
        private ImageView newsImageTwo;
        private ImageView newsImageThree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            newsImageOne = itemView.findViewById(R.id.news_picture_one);
            newsImageTwo = itemView.findViewById(R.id.news_picture_two);
            newsImageThree = itemView.findViewById(R.id.news_picture_three);
        }
    }

}
