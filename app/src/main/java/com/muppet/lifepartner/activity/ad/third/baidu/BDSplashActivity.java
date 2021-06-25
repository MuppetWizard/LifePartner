package com.muppet.lifepartner.activity.ad.third.baidu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baidu.mobad.feeds.RequestParameters;
import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashLpCloseListener;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;

import java.util.HashMap;

public class BDSplashActivity extends AppCompatActivity {

    private FrameLayout flSplash;
    private View skip;

    private SplashAd baiduSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_d_splash);
        initStatusBar();
        flSplash = findViewById(R.id.fl_splash);
        loadBaidu("7528454");
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

        final RequestParameters parameters = new RequestParameters.Builder()
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

        baiduSplash = new com.baidu.mobads.SplashAd(this, flSplash, listener, id, true, parameters,3500);
        tvSkip.setOnClickListener(v -> {
            listener.onAdDismissed();
        });
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        FrameLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

}