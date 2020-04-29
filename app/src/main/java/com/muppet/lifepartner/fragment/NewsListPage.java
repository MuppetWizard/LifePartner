package com.muppet.lifepartner.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.muppet.lifepartner.IpAddress;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActWebview;
import com.muppet.lifepartner.adapter.NewslistAdapter;
import com.muppet.lifepartner.mode.News;
import com.muppet.lifepartner.util.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NewsListPage extends Fragment implements AdapterView.OnItemClickListener{

    @BindView(R.id.news_list)
    ListView newsList;
    Unbinder unbinder;
    @BindView(R.id.progress04)
    ProgressBar progress04;

    private String tyoe;

    private NewslistAdapter newslistAdapter;
    private News news;
    private List<News.ResultBean.DataBean> newsdatas;

    public static NewsListPage newInstance(String tyoe) {

        Bundle args = new Bundle();
        args.putString("type", tyoe);
        NewsListPage fragment = new NewsListPage();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list_page, null);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tyoe = bundle.getString("type");
        }
        newslistAdapter = new NewslistAdapter(getContext());
        newsList.setAdapter(newslistAdapter);
        getList();
        newsList.setOnItemClickListener(this);

        return view;
    }

    private void getList() {
        newsdatas = new ArrayList<>();
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.NEWS,0));
        params.addBodyParameter("key", Constant.NEWS_KEY);
        params.addBodyParameter("type", tyoe);
        x.http().get(params, new Callback.CommonCallback<News>() {
            @Override
            public void onSuccess(News news) {
                if (news != null) {
                    News.ResultBean result = news.getResult();
                    if (result != null) {
                        progress04.setVisibility(View.GONE);
                        List<News.ResultBean.DataBean> newsDatas = result.getData();
                        newsdatas = newsDatas;
                        newslistAdapter.initListData(newsDatas);
                    } else {
                        Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), news.getReason(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = newsdatas.get(position).getUrl();
        Log.e("hahaha", url);
        Intent intent = new Intent(getContext(), ActWebview.class);
        intent.putExtra("URL", url);
        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
