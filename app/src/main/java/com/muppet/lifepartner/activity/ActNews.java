package com.muppet.lifepartner.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.fragment.NewsListPage;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.UIUtils;
import com.youyi.yesdk.ad.BannerAd;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.listener.BannerAdListener;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActNews extends AppCompatActivity {

    @BindView(R.id.nwes_tab)
    TabLayout nwesTab;
    @BindView(R.id.news_pager)
    ViewPager newsPager;
    @BindView(R.id.ad_container)
    FrameLayout flBanner;

    //类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    public static final String[] titles = {"社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    public static final String[] types = {"shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
    @BindView(R.id.back)
    ImageView back;

    private BannerAd bannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news);
        ButterKnife.bind(this);
        newsPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        nwesTab.setupWithViewPager(newsPager);
        loadAd("0000000110");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerAd.destroy();
    }

    private void loadAd(String id) {
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        bannerAd = new BannerAd();
        bannerAd.setBannerConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .setExpressViewAcceptedSize(expressViewWidth,120)
                        .isCarousel(false)
                        .build());
        bannerAd.loadAdBanner(new BannerAdListener() {
            @Override
            public void onLoaded(@Nullable View view) {
                flBanner.addView(view);
            }

            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.e(Constant.TAG,"code:"+integer+" msg: "+ s);
            }

            @Override
            public void onShow() {
                Log.e(Constant.TAG,"onShow");
            }

            @Override
            public void onClosed() {
                Log.e(Constant.TAG,"onClosed");
            }

            @Override
            public void onClicked() {
                Log.e(Constant.TAG,"onClicked");
            }

            @Override
            public void onShowAdOverLay() {
                Log.e(Constant.TAG,"onShowAdOverLay");
            }

            @Override
            public void onAdCloseOverLay() {
                Log.e(Constant.TAG,"onAdCloseOverLay");
            }

            @Override
            public void onDislikeShow() {
                Log.e(Constant.TAG,"onDislikeShow");
            }

            @Override
            public void onDislikeSelected(int i, @Nullable String s) {
                Log.e(Constant.TAG,"onDislikeSelected");
                flBanner.removeAllViews();
            }

            @Override
            public void onDislikeCanceled() {
                Log.e(Constant.TAG,"onDislikeCanceled");
            }
        });
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
