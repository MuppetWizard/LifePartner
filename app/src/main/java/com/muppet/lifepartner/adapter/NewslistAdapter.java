package com.muppet.lifepartner.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.mode.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewslistAdapter extends BaseAdapter {
    private Context context;
    private List<News.ResultBean.DataBean> newslist = new ArrayList<>();

    public NewslistAdapter(Context context) {
        this.context = context;
    }

    public void initListData(List<News.ResultBean.DataBean> newslist) {
        this.newslist = newslist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newslist.size();
    }

    @Override
    public News.ResultBean.DataBean getItem(int position) {
        return newslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.home_news_list, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        News.ResultBean.DataBean item = getItem(position);
        viewHolder.newsTitle.setText(item.getTitle());
        viewHolder.newsAuthor.setText(item.getAuthor_name());
        String thumbnail_pic_s = item.getThumbnail_pic_s();
        if (!TextUtils.isEmpty(thumbnail_pic_s)) {
            Glide.with(context).load(thumbnail_pic_s).into(viewHolder.newsImg);
        }

        return view;
    }

    static
    class ViewHolder {
        @BindView(R.id.news_img)
        ImageView newsImg;
        @BindView(R.id.news_title)
        TextView newsTitle;
        @BindView(R.id.news_author)
        TextView newsAuthor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
