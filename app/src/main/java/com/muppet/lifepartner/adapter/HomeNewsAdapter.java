package com.muppet.lifepartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.mode.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.VH> {
    private Context context;
    private List<News.ResultBean.DataBean> newsDatas = new ArrayList<>();
    private HomeNewsAdapter.OnItemClickListener OnItemClickListener;


    public HomeNewsAdapter(Context context) {
        this.context = context;
    }

    public void setNewsDatas(int pageIndex, List<News.ResultBean.DataBean> list) {
        if (pageIndex == 0) {
            newsDatas.clear();
        }
        //newsDatas.addAll(list);
        this.newsDatas = list;
        notifyDataSetChanged(); //刷新
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_news_list, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH vh, final int position) {
        final News.ResultBean.DataBean news = newsDatas.get(position + 4);
        vh.newsTitle.setText(news.getTitle());
        vh.newsAuthor.setText(news.getAuthor_name());
        Glide.with(context).load(news.getThumbnail_pic_s()).into(vh.newsImg);
        vh.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClickListener != null) {
                    String url = news.getUrl();
                    OnItemClickListener.OnItemClik(vh.itemview ,url);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsDatas.size() - 4;
    }

    public interface OnItemClickListener{
        void OnItemClik(View view, String url);
    }

    public void setOnItemClickListener(HomeNewsAdapter.OnItemClickListener listener) {
        this.OnItemClickListener = listener;
    }
    static
    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.news_img)
        ImageView newsImg;
        @BindView(R.id.news_title)
        TextView newsTitle;
        @BindView(R.id.news_author)
        TextView newsAuthor;
        @BindView(R.id.itemview)
        LinearLayout itemview;

        VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }


}
