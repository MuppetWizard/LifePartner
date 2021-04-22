package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.util.UIUtils;
import com.youyi.yesdk.ad.BannerAd;
import com.youyi.yesdk.listener.BannerAdListener;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends AppCompatActivity {

    @BindView(R.id.fl_banner)
    FrameLayout flBanner;

    @BindView(R.id.adView)
    AdView adView;

    private BannerAd bannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        initStatusBar();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        bindView(R.id.btn_csj_banner);
        bindView(R.id.btn_gdt_banner);
        bindView(R.id.btn_gg_banner);
    }

    private void bindView(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_csj_banner) {
//                    loadBanner("0000000039");
                    //162
                    loadBanner("0000000110");
                }
                if (v.getId() == R.id.btn_gdt_banner){
                    loadBanner("0000000040");
                }
                if (v.getId() == R.id.btn_gg_banner) {
                    loadGGBanner();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bannerAd.destroy();

    }

    private void loadGGBanner() {
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }

    private void loadBanner(String id) {
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        bannerAd = new BannerAd();
        bannerAd.setBannerConfig(this,id, (int) expressViewWidth,120,false);
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
                flBanner.removeAllViews();
            }

            @Override
            public void onDislikeCanceled() {
                Log.e(Constant.TAG,"onDislikeCanceled");
            }
        });

    }
}