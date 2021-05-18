package com.muppet.lifepartner.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.muppet.lifepartner.IpAddress;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActCalendar;
import com.muppet.lifepartner.activity.ActNews;
import com.muppet.lifepartner.activity.ActWebview;
import com.muppet.lifepartner.adapter.HomeNewsAdapter;
import com.muppet.lifepartner.mode.News;
import com.muppet.lifepartner.App;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.MyImageLoader;
import com.muppet.lifepartner.util.UIUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youyi.yesdk.ad.BannerAd;
import com.youyi.yesdk.ad.FullVideoAd;
import com.youyi.yesdk.ad.YOUEAdConstants;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.business.UEAdManager;
import com.youyi.yesdk.listener.BannerAdListener;
import com.youyi.yesdk.listener.FullVideoListener;

import org.jetbrains.annotations.Nullable;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;


public class HomePage extends SupportFragment implements OnBannerListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.home_list)
    RecyclerView homeList;
    Unbinder unbinder;
    @BindView(R.id.progress03)
    ProgressBar progress03;
    @BindView(R.id.home_news)
    RelativeLayout homeNews;
    @BindView(R.id.home_calender)
    RelativeLayout homeCalender;
    /*@BindView(R.id.adView)
    AdView adView;*/
    @BindView(R.id.fl_banner)
    FrameLayout flBanner;

    private int pageIndex = 0;//页码
    private HomeNewsAdapter homeNewsAdapter;
    private LinearLayoutManager recyclermanager;

    private FullVideoAd fullVideoAd;
    private BannerAd bannerAd;

    List<String> list_title;
    List<String> list_image;
    private List<News.ResultBean.DataBean> newsDatas;
    private String getkey = "3e9494f99e25d9576e38679010532d29";
    //private String getkey = "96853af52c671377a7c6eeb8d797800b";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.home_page, null);
        ButterKnife.bind(this, view);
        homeNewsAdapter = new HomeNewsAdapter(getContext());
        homeList.setAdapter(homeNewsAdapter);
        getList();
        initRecyclerView();
        unbinder = ButterKnife.bind(this, view);
        /*MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/
        loadBanner("0000000039");
//        loadFullScreenVideo("0000000046", YOUEAdConstants.VERTICAL);
        return view;
    }
    private void loadBanner(String id) {
        float expressViewWidth = UIUtils.getScreenWidthDp(getActivity());
        bannerAd = new BannerAd();
//        bannerAd.setBannerConfig(this,id, (int) expressViewWidth,120,true);
        bannerAd.setBannerConfig(getActivity(),
                new AdPlacement.Builder()
                        .setAdId(id)
                        .setExpressViewAcceptedSize(expressViewWidth,120)
                        .isCarousel(false)
                        .build()
        );
        bannerAd.loadAdBanner(flBanner, new BannerAdListener() {
            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.e(Constant.TAG,"code:"+integer+" msg: "+ s);
            }

            @Override
            public void onShow() {
                Log.e(Constant.TAG,"onShow");
//                setDownloadListener(bannerAd);
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
    private void loadFullScreenVideo(String id, int orientation) {
        fullVideoAd = new FullVideoAd();
        fullVideoAd.setVideoConfig(getActivity(),
                new UEAdManager()
                        .setExpressViewAcceptedSize(500,500)
                        .setOrientation(orientation)
                        .setMinVideoDuration(5)
                        .setMaxVideoDuration(20)
                        .build());
        fullVideoAd.loadFullVideo(id, new FullVideoListener() {
            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.d(Constant.TAG,"onError : code: "+ integer+" msg : "+ s);
            }

            @Override
            public void onAdLoaded() {
                Log.d(Constant.TAG,"onAdLoaded");
            }

            @Override
            public void onAdCached() {
                Log.d(Constant.TAG,"onAdCached");
                fullVideoAd.show();
//                setDownloadListener(fullVideoAd);
            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow");
            }

            @Override
            public void onAdSkipped() {
                Log.d(Constant.TAG,"onAdSkipped");
            }

            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked");
            }

            @Override
            public void onAdComplete() {
                Log.d(Constant.TAG,"onAdComplete");
            }

            @Override
            public void onAdClosed() {
                Log.d(Constant.TAG,"onAdClosed");
            }
        });
    }
    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        recyclermanager = new LinearLayoutManager(App.getAppContext(), LinearLayoutManager.VERTICAL, false);
        homeList.setLayoutManager(recyclermanager);

        homeNewsAdapter.setOnItemClickListener(new HomeNewsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClik(View view, String url) {
                Intent intent = new Intent(getContext(), ActWebview.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });
    }

    //请求网络数据
    private void getList() {
        list_title = new ArrayList<>();
        list_image = new ArrayList<>();
        newsDatas = new ArrayList<>();
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.NEWS,0));
        //传递参数
        params.addParameter("key", Constant.NEWS_KEY);
        params.addParameter("type", "top");

        //请求
        x.http().get(params, new Callback.CommonCallback<News>() {
            @Override
            public void onSuccess(News result) {

                //请求成功，设置数据
                if (result != null) {
                    News.ResultBean news = result.getResult();
                    if (news != null) {
                        progress03.setVisibility(View.GONE);
                        newsDatas = news.getData();
                        for (int i = 0; i <= 3; i++) {
                            list_title.add(newsDatas.get(i).getTitle());
                            list_image.add(newsDatas.get(i).getThumbnail_pic_s());
                        }
                        initView(banner);
                        //NewsList显示的数据
                        homeNewsAdapter.setNewsDatas(pageIndex, newsDatas);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("123", ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 初始化轮播器  设置轮播器相关属性
     */
    private void initView(Banner banner) {
        //设置样式，里面有很多种样式可以自己都看看效果
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new MyImageLoader());
        //动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播标题
        banner.setBannerTitles(list_title);
        //设置播放时间间隔
        banner.setDelayTime(5000);
        //自动播放
        banner.isAutoPlay(true);
        //设置图片加载器
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置轮播图片  轮播监听器
        banner.setImages(list_image)
                .setOnBannerListener(this)
                .start();
    }

    /**
     * 轮播器监听
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        String url = newsDatas.get(position).getUrl();
        Intent intent = new Intent(getContext(), ActWebview.class);
        intent.putExtra("URL",url);
        startActivity(intent);
    }

    @OnClick({R.id.home_news, R.id.home_calender})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_news:
                Intent intent = new Intent(getContext(), ActNews.class);
                startActivity(intent);
                break;
            case R.id.home_calender:
                Intent intent2 = new Intent(getContext(), ActCalendar.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
