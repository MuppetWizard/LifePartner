package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.comm.util.AdError;
import com.youyi.yesdk.ad.InterstitialAd;
import com.youyi.yesdk.listener.InterstitialAdListener;
import com.youyi.yesdk.listener.InterstitialMediaListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InterstitialActivity extends AppCompatActivity {

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        ButterKnife.bind(this);
        initStatusBar();
        bindView(R.id.btn_vertical_cha);
        bindView(R.id.btn_horizontal_cha);
    }

    private void bindView(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_vertical_cha) {
                    loadInterstitial("0000000041",true);
                }
                if (v.getId() == R.id.btn_horizontal_cha){
                    loadInterstitial("0000000112",false);
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        interstitialAd.destroy();
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }


    private void loadInterstitial(String id,boolean vertical) {
        interstitialAd = new InterstitialAd();
        interstitialAd.setInterstitialAdConfig(this,id,vertical,5,61);
        VideoOption option = new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS)
                .setAutoPlayMuted(false)
                .setDetailPageMuted(false)
                .build();
        interstitialAd.loadInterstitialAd(option, new InterstitialAdListener() {

            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.e(Constant.TAG,"code:"+integer+" msg: "+ s);
            }

            @Override
            public void onAdLoaded() {
                interstitialAd.show();
                Log.e(Constant.TAG,"onAdLoaded");
            }

            @Override
            public void onAdShow() {
                Log.e(Constant.TAG,"onAdShow");
            }

            @Override
            public void onAdClosed() {
                Log.e(Constant.TAG,"onAdClosed");
            }

            @Override
            public void onAdClicked() {
                Log.e(Constant.TAG,"onAdClicked");
            }
        });

        interstitialAd.setMediaListener(new InterstitialMediaListener() {
            @Override
            public void onVideoInit() {
                Log.e(Constant.TAG,"onVideoInit");
            }

            @Override
            public void onVideoLoading() {
                Log.e(Constant.TAG,"onVideoLoading");
            }

            @Override
            public void onVideoReady(long l) {
                Log.e(Constant.TAG,"onVideoReady"+l);
            }

            @Override
            public void onVideoStart() {
                Log.e(Constant.TAG,"onVideoStart");
            }

            @Override
            public void onVideoPause() {
                Log.e(Constant.TAG,"onVideoPause");
            }

            @Override
            public void onVideoComplete() {
                Log.e(Constant.TAG,"onVideoComplete");
            }

            @Override
            public void onVideoError(AdError adError) {
                Log.e(Constant.TAG,
                        "onVideoError:"+adError.getErrorCode()+" msg:"+adError.getErrorMsg());
            }
            @Override
            public void onVideoPageOpen() {
                Log.e(Constant.TAG,"onVideoPageOpen");
            }
            @Override
            public void onVideoPageClose() {
                Log.e(Constant.TAG,"onVideoPageClose");
            }
        });
    }
}