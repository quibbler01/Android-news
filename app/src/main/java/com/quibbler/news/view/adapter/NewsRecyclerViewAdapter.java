package com.quibbler.news.view.adapter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.quibbler.news.NewsApplication;
import com.quibbler.news.R;
import com.quibbler.news.model.DataReport;
import com.quibbler.news.model.TaskHandler;
import com.quibbler.news.model.bean.NewsDataBean;
import com.quibbler.news.model.database.NewsCacheModel;
import com.quibbler.news.view.NewsDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolderBase> {
    private static final String TAG = "TAG_NewsRecyclerViewAdapter";

    private Context mContext;
    private int mNewsType = 0;
    private List<NewsDataBean> mNewsData = new ArrayList<>();
    private RequestOptions mImageGlideCorner = null;
    private static final int CORNERS = 8;

    public NewsRecyclerViewAdapter(Context context) {
        this.mContext = context;
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(CORNERS);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        mImageGlideCorner = RequestOptions.bitmapTransform(roundedCorners);
    }

    public NewsRecyclerViewAdapter(Context mContext, List<NewsDataBean> mNewsData) {
        this.mContext = mContext;
        this.mNewsData = mNewsData;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsRecyclerViewAdapter.ViewHolderBase viewHolder = null;
        switch (viewType) {
            case NewsDataBean.TYPE_ONE:
                View viewOne = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_first_view, parent, false);
                viewHolder = new ViewHolderBase(viewOne);
                break;
            case NewsDataBean.TYPE_ZERO:
            case NewsDataBean.TYPE_TWO:
            case NewsDataBean.TYPE_THREE:
            default:
                View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_view, parent, false);
                viewHolder = new ViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.ViewHolderBase holder, int position) {
        final NewsDataBean newsData = mNewsData.get(position);
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mOnClickListener);
        holder.deleteBtn.setTag(position);
        holder.deleteBtn.setOnClickListener(mOnClickListener);
        //title
        holder.title.setText(Html.fromHtml(newsData.getTitle()));
        //author、category、time
        holder.author.setText(newsData.getAuthor_name());
        holder.category.setText(newsData.getCategory());
        holder.date.setText(newsData.getDate());

        int type = getItemViewType(position);
        switch (type) {
            case NewsDataBean.TYPE_ONE:
                holder.title.setText(Html.fromHtml(newsData.getTitle()));
//                Glide.with(NewsApplication.getApplication()).load(newsData.getThumbnail_pic_s()).placeholder(R.drawable.news_item_picture_default).into(holder.newsImageOne);
                setImageByGlide(holder.newsImageOne, newsData.getThumbnail_pic_s());
                break;
            case NewsDataBean.TYPE_ZERO:
            case NewsDataBean.TYPE_TWO:
            case NewsDataBean.TYPE_THREE:
            default:
                ViewHolder viewHolder = (ViewHolder) holder;
                //image
                if (TextUtils.isEmpty(newsData.getThumbnail_pic_s()) && TextUtils.isEmpty(newsData.getThumbnail_pic_s02()) && TextUtils.isEmpty(newsData.getThumbnail_pic_s03())) {
                    viewHolder.pictureArea.setVisibility(View.GONE);
                } else {
                    viewHolder.pictureArea.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(newsData.getThumbnail_pic_s())) {
                        viewHolder.newsImageOne.setVisibility(View.VISIBLE);
//                        Glide.with(NewsApplication.getApplication()).load(newsData.getThumbnail_pic_s()).placeholder(R.drawable.news_item_picture_default).into(viewHolder.newsImageOne);
                        setImageByGlide(viewHolder.newsImageOne, newsData.getThumbnail_pic_s());
                    }
                    if (!TextUtils.isEmpty(newsData.getThumbnail_pic_s02())) {
                        viewHolder.newsImageTwo.setVisibility(View.VISIBLE);
//                        Glide.with(NewsApplication.getApplication()).load(newsData.getThumbnail_pic_s02()).placeholder(R.drawable.news_item_picture_default).into(viewHolder.newsImageTwo);
                        setImageByGlide(viewHolder.newsImageTwo, newsData.getThumbnail_pic_s02());
                    }
                    if (!TextUtils.isEmpty(newsData.getThumbnail_pic_s03())) {
                        viewHolder.newsImageThree.setVisibility(View.VISIBLE);
//                        Glide.with(NewsApplication.getApplication()).load(newsData.getThumbnail_pic_s03()).placeholder(R.drawable.news_item_picture_default).into(viewHolder.newsImageThree);
                        setImageByGlide(viewHolder.newsImageThree, newsData.getThumbnail_pic_s03());
                    }
                }
                break;
        }
    }

    private void setImageByGlide(ImageView image, String ulr) {
        Glide.with(NewsApplication.getApplication()).load(ulr).apply(mImageGlideCorner).placeholder(R.drawable.news_item_picture_default).into(image);
    }

    @Override
    public int getItemCount() {
        return mNewsData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mNewsData.get(position).getNewsType();
    }

    public void updateData(@NonNull List<NewsDataBean> newsData) {
        mNewsData.clear();
        mNewsData.addAll(newsData);
        notifyDataSetChanged();
    }

    public static class ViewHolderBase extends RecyclerView.ViewHolder {
        //title
        TextView title;
        //image
        ImageView newsImageOne;
        //description
        TextView author;
        TextView category;
        TextView date;
        ImageView deleteBtn;

        public ViewHolderBase(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            newsImageOne = itemView.findViewById(R.id.news_picture_one);
            author = itemView.findViewById(R.id.author_name);
            category = itemView.findViewById(R.id.category);
            date = itemView.findViewById(R.id.date);
            deleteBtn = itemView.findViewById(R.id.delete);
        }
    }

    public static class ViewHolder extends ViewHolderBase {
        //image
        View pictureArea;
        ImageView newsImageTwo;
        ImageView newsImageThree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureArea = itemView.findViewById(R.id.picture_container);
            newsImageTwo = itemView.findViewById(R.id.news_picture_two);
            newsImageThree = itemView.findViewById(R.id.news_picture_three);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            final NewsDataBean newsDataBean = mNewsData.get(position);
            switch (v.getId()) {
                case R.id.delete:
                    Log.d(TAG, "delete");
                    mNewsData.remove(position);
                    notifyDataSetChanged();
                    Snackbar.make(v, R.string.news_delete_toast, 750).show();
                    TaskHandler.executeUpdateTask(new Runnable() {
                        @Override
                        public void run() {
                            NewsCacheModel.deleteNewsItem(newsDataBean.getUniquekey());
                        }
                    });
                    break;
                default:
                    if (null == newsDataBean) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(mContext, NewsDetailActivity.class));
                    String url = newsDataBean.getUrl();
                    String title = newsDataBean.getTitle();
                    intent.putExtra(NewsDetailActivity.NEWS_URL, url);
                    intent.putExtra(NewsDetailActivity.NEWS_TITLE, title.substring(0, Math.min(title.length(), NewsDetailActivity.NEWS_TITLE_LENGTH)));
                    ((Activity) mContext).startActivity(intent);
                    //report click event
                    Map<String, String> clickEventValues = new HashMap<>();
                    clickEventValues.put(DataReport.NEWS_ITEM_CLICK_TIME, String.valueOf(System.currentTimeMillis()));
                    clickEventValues.put(DataReport.NEWS_ITEM_CLICK_TITLE, title);
                    clickEventValues.put(DataReport.NEWS_ITEM_CLICK_URL, url);
                    DataReport.reportOnClickEvent(DataReport.NEWS_ITEM_CLICK, clickEventValues);
                    break;
            }
        }
    };


}
