package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.youyi.yesdk.ad.FullVideoAd;
import com.youyi.yesdk.business.UEAdManager;
import com.youyi.yesdk.business.YOUEAdConstants;
import com.youyi.yesdk.listener.FullVideoListener;

import org.jetbrains.annotations.Nullable;

public class FullscreenVideoActivity extends AppCompatActivity {

    private FullVideoAd fullVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);
        initStatusBar();
        initView();
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }

    private void initView() {
        findViewById(R.id.btn_fullscreen_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFullScreenVideo("0000000046");
            }
        });
    }
    private void loadFullScreenVideo(String id) {
        fullVideoAd = new FullVideoAd();
        fullVideoAd.setVideoConfig(this,
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

}