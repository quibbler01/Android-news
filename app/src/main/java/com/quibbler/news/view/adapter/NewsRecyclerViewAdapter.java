package com.quibbler.news.view.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        //title
        holder.title.setText(Html.fromHtml(newsData.getTitle()));
        //image
        if (TextUtils.isEmpty(newsData.getThumbnail_pic_s()) && TextUtils.isEmpty(newsData.getThumbnail_pic_s02()) && TextUtils.isEmpty(newsData.getThumbnail_pic_s03())) {
            holder.pictureArea.setVisibility(View.GONE);
        } else {
            holder.pictureArea.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(newsData.getThumbnail_pic_s())) {
                holder.newsImageOne.setVisibility(View.VISIBLE);
                Glide.with(NewsApplication.getApplication()).load(newsData.getThumbnail_pic_s()).placeholder(R.drawable.ic_launcher_background).into(holder.newsImageOne);
            }
            if (!TextUtils.isEmpty(newsData.getThumbnail_pic_s02())) {
                holder.newsImageTwo.setVisibility(View.VISIBLE);
                Glide.with(NewsApplication.getApplication()).load(newsData.getThumbnail_pic_s02()).placeholder(R.drawable.ic_launcher_background).into(holder.newsImageTwo);
            }
            if (!TextUtils.isEmpty(newsData.getThumbnail_pic_s03())) {
                holder.newsImageThree.setVisibility(View.VISIBLE);
                Glide.with(NewsApplication.getApplication()).load(newsData.getThumbnail_pic_s03()).placeholder(R.drawable.ic_launcher_background).into(holder.newsImageThree);
            }
        }
        //author、category、time
        holder.author.setText(newsData.getAuthor_name());
        holder.category.setText(newsData.getCategory());
        holder.date.setText(newsData.getDate());
    }

    @Override
    public int getItemCount() {
        return mNewsData.size();
    }

    public void updateData(@NonNull List<NewsDataBean> newsData) {
        mNewsData.clear();
        mNewsData.addAll(newsData);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //title
        private TextView title;
        //image
        private View pictureArea;
        private ImageView newsImageOne;
        private ImageView newsImageTwo;
        private ImageView newsImageThree;
        //description
        private TextView author;
        private TextView category;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            pictureArea = itemView.findViewById(R.id.picture_container);
            newsImageOne = itemView.findViewById(R.id.news_picture_one);
            newsImageTwo = itemView.findViewById(R.id.news_picture_two);
            newsImageThree = itemView.findViewById(R.id.news_picture_three);
            author = itemView.findViewById(R.id.author_name);
            category = itemView.findViewById(R.id.category);
            date = itemView.findViewById(R.id.date);
        }
    }

}
