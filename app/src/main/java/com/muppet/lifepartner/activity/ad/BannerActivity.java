package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.listeners.BannerAdEventListener;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.util.UIUtils;
import com.youyi.yesdk.ad.BannerAd;
import com.youyi.yesdk.listener.BannerAdListener;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends AppCompatActivity {

    @BindView(R.id.fl_banner)
    FrameLayout flBanner;

    @BindView(R.id.adView)
    AdView adView;


    private BannerAd bannerAd;
    private InMobiBanner iBanner;

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
        RequestConfiguration configuration = new RequestConfiguration.Builder()
                .setTestDeviceIds(Collections.singletonList(""))
                .build();
        MobileAds.setRequestConfiguration(configuration);

        bindView(R.id.btn_csj_banner);
        bindView(R.id.btn_gdt_banner);
        bindView(R.id.btn_gg_banner);
        bindView(R.id.btn_imb_banner);
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
                if (v.getId() == R.id.btn_imb_banner) {
                    loadInMoBi(1621364045212L);
                    //测试
//                    loadInMoBi(1473189489298L);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bannerAd != null) {
            bannerAd.destroy();
        }
        if (adView != null) {
            adView.destroy();
        }
        if (iBanner != null) {
            iBanner.destroy();
        }
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

    private void loadInMoBi(long id) {
        iBanner = new InMobiBanner(this,id);
        iBanner.setAnimationType(InMobiBanner.AnimationType.ROTATE_HORIZONTAL_AXIS);
        iBanner.setRefreshInterval(10);
        iBanner.setListener(new BannerAdEventListener() {
            @Override
            public void onAdFetchFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onAdFetchFailed(inMobiBanner, inMobiAdRequestStatus);
                Log.e(Constant.TAG,
                        "code:"+inMobiAdRequestStatus.getStatusCode()+ "msg:"+inMobiAdRequestStatus.getMessage());
            }

            @Override
            public void onAdLoadSucceeded(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                Log.d(Constant.TAG,"onAdLoadSucceeded");
            }

            @Override
            public void onAdClicked(@NonNull InMobiBanner inMobiBanner, Map<Object, Object> map) {
                super.onAdClicked(inMobiBanner, map);
                Log.d(Constant.TAG,"onAdClicked");
            }

            @Override
            public void onAdDisplayed(@NonNull InMobiBanner inMobiBanner) {
                super.onAdDisplayed(inMobiBanner);
                Log.d(Constant.TAG,"onAdDisplayed");
            }

            @Override
            public void onAdDismissed(@NonNull InMobiBanner inMobiBanner) {
                super.onAdDismissed(inMobiBanner);
                Log.d(Constant.TAG,"onAdDismissed");
            }

            @Override
            public void onUserLeftApplication(@NonNull InMobiBanner inMobiBanner) {
                super.onUserLeftApplication(inMobiBanner);
                Log.d(Constant.TAG,"onUserLeftApplication");
            }

            @Override
            public void onRewardsUnlocked(@NonNull InMobiBanner inMobiBanner, Map<Object, Object> map) {
                super.onRewardsUnlocked(inMobiBanner, map);
                Log.d(Constant.TAG,"onRewardsUnlocked");
            }
        });
        int width = toPixelUnits(320);
        int height = toPixelUnits(50);
        FrameLayout.LayoutParams bannerLp = new FrameLayout.LayoutParams(width,height );
        bannerLp.gravity = Gravity.CENTER_HORIZONTAL;
        flBanner.removeAllViews();
        flBanner.addView(iBanner,bannerLp);
        iBanner.load();
    }

    private void loadGGBanner() {
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        int height = toPixelUnits(60);
        AdRequest adRequest = new AdRequest.Builder().build();

        AdView mAdView = new AdView(this);
        AdSize adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, (int) expressViewWidth);
        mAdView.setAdSize(adSize);
        //测试广告位id
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
//        mAdView.setAdUnitId("ca-app-pub-2343173165030471/3204977521");
//        FrameLayout.LayoutParams bannerLp = new FrameLayout.LayoutParams((int) expressViewWidth,height );
//        bannerLp.gravity = Gravity.CENTER_HORIZONTAL;
        flBanner.removeAllViews();
        flBanner.addView(mAdView);
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d(Constant.TAG,"onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(Constant.TAG,"onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                Log.d(Constant.TAG,"onAdOpened");
            }

            @Override
            public void onAdLoaded() {
                Log.d(Constant.TAG,"onAdLoaded");
            }

            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked");
            }

            @Override
            public void onAdImpression() {
                Log.d(Constant.TAG,"onAdImpression");
            }
        });
    }

    private int toPixelUnits(int dipUnit) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dipUnit * density);
    }
}