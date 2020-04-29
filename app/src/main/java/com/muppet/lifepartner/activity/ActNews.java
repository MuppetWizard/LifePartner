package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.fragment.NewsListPage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActNews extends AppCompatActivity {

    @BindView(R.id.nwes_tab)
    TabLayout nwesTab;
    @BindView(R.id.news_pager)
    ViewPager newsPager;

    //类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    public static final String[] titles = {"社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    public static final String[] types = {"shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
    @BindView(R.id.back)
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news);
        ButterKnife.bind(this);
        newsPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        nwesTab.setupWithViewPager(newsPager);

    }
    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            NewsListPage fragment = NewsListPage.newInstance(types[position]);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
