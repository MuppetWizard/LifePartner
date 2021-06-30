package com.muppet.lifepartner.activity.ad.third.baidu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baidu.mobad.feeds.RequestParameters;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashLpCloseListener;
import com.baidu.mobads.sdk.api.SplashInteractionListener;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;

import java.util.HashMap;

public class BDSplashActivity extends AppCompatActivity {

    private FrameLayout flSplash;
    private View skip;

    private SplashAd baiduSplash;
    private RequestParameters parameters;

    private com.baidu.mobads.sdk.api.SplashAd splashPlus;
    private com.baidu.mobads.sdk.api.RequestParameters parametersPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_d_splash);
        initStatusBar();
        flSplash = findViewById(R.id.fl_splash);
        init();

    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("v")) {
            int key = intent.getIntExtra("v",-1);
            switch (key) {
                case 100:
                    loadBaidu("7528454");
                    break;
                case 200://plus
                    loadBaiduPlus("7528454");
                    break;
            }
        }

    }

    private void loadBaiduPlus(String id) {
        final SplashInteractionListener listener = new SplashInteractionListener() {
            @Override
            public void onAdPresent() {
                Log.d(Constant.TAG,"onAdPresent");
            }

            @Override
            public void onAdClick() {
                Log.d(Constant.TAG,"onAdClick");
            }

            @Override
            public void onADLoaded() {
                Log.d(Constant.TAG,"onADLoaded");
            }

            @Override
            public void onLpClosed() {
                Log.d(Constant.TAG,"onLpClosed");
                finish();
            }

            @Override
            public void onAdDismissed() {
                Log.d(Constant.TAG,"onAdDismissed");
                finish();
            }

            @Override
            public void onAdFailed(String s) {
                Log.d(Constant.TAG,"onAdFailed: "+s);
                finish();
            }
        };

        parametersPlus = new com.baidu.mobads.sdk.api.RequestParameters.Builder()
                //设置超时
                .addExtra(com.baidu.mobads.sdk.api.SplashAd.KEY_TIMEOUT,"4200")
                //显示下载信息
                .addExtra(com.baidu.mobads.sdk.api.SplashAd.KEY_DISPLAY_DOWNLOADINFO,"true")
                // 限制点击区域
                .addExtra(com.baidu.mobads.sdk.api.SplashAd.KEY_LIMIT_REGION_CLICK,"true")
                //用户点击开屏下载类广告时，是否弹出Dialog,会覆盖掉 {SplashAd.KEY_DISPLAY_DOWNLOADINFO} 的设置
                .addExtra(com.baidu.mobads.sdk.api.SplashAd.KEY_POPDIALOG_DOWNLOAD, "true")
                .build();
        splashPlus = new com.baidu.mobads.sdk.api.SplashAd(this,id,parametersPlus, listener);
        splashPlus.loadAndShow(flSplash);
    }

    private void loadBaidu(String id) {
        skip = getLayoutInflater().inflate(R.layout.btn_skip,null);

        TextView tvSkip = skip.findViewById(R.id.ue_tv_skip);

        skip.setVisibility(View.GONE);
        CountDownTimer timer =  new CountDownTimer(5 * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSkip.setText(String.format(getResources().getString(R.string.ue_skip),millisUntilFinished / 1000 + 1) );
            }

            @Override
            public void onFinish() {

            }
        };

        parameters = new RequestParameters.Builder()
                .setHeight(1920)
                .setWidth(1080)
                .build();
        final SplashLpCloseListener listener = new SplashLpCloseListener() {
            @Override
            public void onLpClosed() {
                Log.d(Constant.TAG,"onLpClosed");
                finish();
            }

            @Override
            public void onAdPresent() {
                Log.d(Constant.TAG,"onAdPresent");
                flSplash.addView(skip);
                skip.setVisibility(View.VISIBLE);
                timer.start();
            }

            @Override
            public void onAdDismissed() {
                Log.d(Constant.TAG,"onAdDismissed");
                timer.cancel();
                finish();
            }

            @Override
            public void onADLoaded() {
                Log.d(Constant.TAG,"onADLoaded");
                HashMap map = baiduSplash.getExtData();
                Log.d(Constant.TAG,"data  "+map.toString());
            }

            @Override
            public void onAdFailed(String s) {
                Log.d(Constant.TAG,"onAdFailed  "+s);
                timer.cancel();
                finish();
            }

            @Override
            public void onAdClick() {
                Log.d(Constant.TAG,"onAdClick");
            }
        };

        baiduSplash = new SplashAd(this, flSplash, listener, id, true, parameters,3500);
        tvSkip.setOnClickListener(v -> {
            listener.onAdDismissed();
        });
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        FrameLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (splashPlus != null) {
            splashPlus.destroy();
            splashPlus = null;
        }
        if (baiduSplash != null) {
            baiduSplash.destroy();
            baiduSplash = null;
        }
    }
}