package com.muppet.lifepartner.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActAboutUs;
import com.muppet.lifepartner.activity.ActMyFeadback;
import com.muppet.lifepartner.activity.ActWebview;
import com.muppet.lifepartner.activity.ad.DrawStreamAdActivity;
import com.muppet.lifepartner.activity.ad.DrawStreamListActivity;
import com.muppet.lifepartner.activity.ad.StreamAdActivity;
import com.muppet.lifepartner.util.Constant;
import com.youyi.yesdk.ad.BannerAd;
import com.youyi.yesdk.ad.FullVideoAd;
import com.youyi.yesdk.ad.RewardVideoAd;
import com.youyi.yesdk.business.UEAdManager;
import com.youyi.yesdk.business.YOUEAdConstants;
import com.youyi.yesdk.listener.BannerAdListener;
import com.youyi.yesdk.listener.FullVideoListener;
import com.youyi.yesdk.listener.RewardListener;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MePage extends SupportFragment {


    @BindView(R.id.my_about_as)
    LinearLayout myAboutAs;
    @BindView(R.id.my_feedback)
    LinearLayout myFeedback;
    @BindView(R.id.protocol)
    LinearLayout protocol;
    @BindView(R.id.ll_reward_csj)
    LinearLayout llRewardCsj;
    @BindView(R.id.ll_reward_gdt)
    LinearLayout llRewardGdt;
    @BindView(R.id.banner)
    FrameLayout flBanner;
    @BindView(R.id.ll_full_video)
    LinearLayout llFullVideo;
    @BindView(R.id.ll_stream_ad)
    LinearLayout llStreamAd;
    @BindView(R.id.ll_draw_stream_ad)
    LinearLayout llDrawStreamAd;
    @BindView(R.id.ll_test_stream_ad)
    LinearLayout llStreamAdTest;
    @BindView(R.id.ll_test_draw_stream_ad)
    LinearLayout llDrawStreamAdTest;

    private BannerAd bannerAd;
    private FullVideoAd fullVideoAd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.me_page, null);
        ButterKnife.bind(this,view);
        loadBanner();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bannerAd.destroy();

    }

    private void loadBanner() {
        bannerAd = new BannerAd();
        bannerAd.setBannerConfig(getActivity(),"0000000039",500,108,false);
        bannerAd.loadAdBanner(flBanner, new BannerAdListener() {
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
            }

            @Override
            public void onDislikeCanceled() {
                Log.e(Constant.TAG,"onDislikeCanceled");
            }
        });
    }

    private void loadRewardVideo(String id) {
        RewardVideoAd ad = new RewardVideoAd();
        ad.setRewardConfig(getActivity(),new UEAdManager()
//                .setUserID("youe_TEST")
//                .setCustomData("youe_data")
                .setExpressViewAcceptedSize(500f,500f)
                .setScenes(YOUEAdConstants.RitScenes.CUSTOMIZE_SCENES,"scenes_test")
                .setOrientation(YOUEAdConstants.HORIZONTAL)
                .build()
        ).loadRewardVideo(id, new RewardListener() {
            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.d(Constant.TAG,"onError : code: "+ integer+" msg : "+ s);
            }

            @Override
            public void onVideoCached() {
                Log.d(Constant.TAG,"onVideoCached");
                ad.show();
            }

            @Override
            public void onADLoaded() {
                Log.d(Constant.TAG,"onADLoaded");
            }

            @Override
            public void onADShow() {
                Log.d(Constant.TAG,"onADShow");
            }

            @Override
            public void onReward(@Nullable Boolean aBoolean, @Nullable Integer integer, @Nullable String s, @Nullable Integer integer1, @Nullable String s1, @Nullable Map<String, Object> map) {
                Log.d(Constant.TAG,"onReward " + aBoolean +" id: "+integer+" msg: "+ s+ " errorC: "+ integer+ " errorM: "+ s1);
            }

            @Override
            public void onADComplete() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onVideoBarClick() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onSKipVideo() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onClosed() {
                Log.d(Constant.TAG,"onADComplete");
            }
        });

    }

    private void loadFullScreenVideo(String id) {
        fullVideoAd = new FullVideoAd();
        fullVideoAd.setVideoConfig(getActivity(),
                new UEAdManager()
                        .setExpressViewAcceptedSize(500,500)
                        .setOrientation(YOUEAdConstants.VERTICAL)
                        .setMinVideoDuration(5)
                        .setMaxVideoDuration(61)
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

    @OnClick({R.id.my_about_as, R.id.my_feedback, R.id.protocol,R.id.ll_reward_csj,
            R.id.ll_reward_gdt,R.id.ll_full_video, R.id.ll_stream_ad,R.id.ll_draw_stream_ad,
            R.id.ll_test_stream_ad,R.id.ll_test_draw_stream_ad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_about_as:
                startActivity(new Intent(getContext(), ActAboutUs.class));
                break;
            case R.id.my_feedback:
                startActivity(new Intent(getContext(), ActMyFeadback.class));
                break;
            case R.id.protocol:
                Intent intent = new Intent(getContext(), ActWebview.class);
                intent.putExtra("URL","https://www.hlhjapp.com/website/agreement/137");
                startActivity(intent);
                break;
            case R.id.ll_reward_csj:
                loadRewardVideo("0000000034");
                break;
            case R.id.ll_reward_gdt:
                loadRewardVideo("0000000033");
                break;
            case R.id.ll_full_video:
                loadFullScreenVideo("0000000046");
                break;
            case R.id.ll_stream_ad:
                startActivity(new Intent(getContext(), StreamAdActivity.class));
                break;
            case R.id.ll_draw_stream_ad:
                startActivity(new Intent(getContext(), DrawStreamAdActivity.class));
                break;
            case R.id.ll_test_stream_ad:

                break;
            case R.id.ll_test_draw_stream_ad:
                startActivity(new Intent(getContext(), DrawStreamListActivity.class));
                break;
        }
    }


}
