package com.muppet.lifepartner.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsSplashScreenAd;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ad.SplashVPlusAd;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.CookieUtil;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.view.UserA;
import com.youyi.yesdk.ad.SplashAd;
import com.youyi.yesdk.listener.SplashListener;

public class ActStart extends AppCompatActivity{

    private int mCount;

    private SplashAd splashAd;
    private FrameLayout flSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start);
        initStatusBar();
        flSplash = findViewById(R.id.fl_splash);
        mCount = (int) CookieUtil.get("isFirst",0);
        if (mCount == 0) {
            UserA dialog = new UserA(this);
            dialog.show();
        }else {
            loadSplash("0000000032");
//            loadKsSplashAd(4000000042L);
        }
    }
    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        FrameLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

    private void loadKsSplashAd(long id) {
        KsScene scene = new KsScene.Builder(id).build();
        KsAdSDK.getLoadManager().loadSplashScreenAd(scene, new KsLoadManager.SplashScreenAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.d(Constant.TAG,"code: "+i+" msg: "+s );
                Intent intent = new Intent(ActStart.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onRequestResult(int i) {
                Log.d(Constant.TAG,"onRequestResult: "+i );
            }

            @Override
            public void onSplashScreenAdLoad(@Nullable KsSplashScreenAd ksSplashScreenAd) {
                Log.d(Constant.TAG,"onSplashScreenAdLoad: " );
//                addFragment(ksSplashScreenAd);
                if (ksSplashScreenAd != null) {
                    flSplash.removeAllViews();
                    flSplash.addView(ksSplashScreenAd.getView(ActStart.this,bindListener()));
                }
            }
        });

    }

    private KsSplashScreenAd.SplashScreenAdInteractionListener bindListener() {
        return new KsSplashScreenAd.SplashScreenAdInteractionListener() {
            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked" );

            }

            @Override
            public void onAdShowError(int i, String s) {
                Log.d(Constant.TAG,"onAdShowError "+i+" msg: "+s );
                gotoMainActivity();
            }

            @Override
            public void onAdShowEnd() {
                Log.d(Constant.TAG,"onAdShowEnd" );
                gotoMainActivity();
            }

            @Override
            public void onAdShowStart() {
                Log.d(Constant.TAG,"onAdShowStart" );
            }

            @Override
            public void onSkippedAd() {
                Log.d(Constant.TAG,"onSkippedAd" );
                gotoMainActivity();
            }
        };
    }


    private void loadSplash(String id) {
        splashAd = new SplashAd();
        splashAd.setSplashConfig(this, id, false, 3500);
        splashAd.loadSplashAd(flSplash, new SplashListener() {
            @Override
            public void onError(Integer integer, String s) {
                Log.d(Constant.TAG,"code: "+integer+" msg: "+s );
                Toast.makeText(ActStart.this,"code: "+integer+" msg: "+s,Toast.LENGTH_SHORT).show();
                gotoMainActivity();
            }

            @Override
            public void onTimeOut() {
                Log.d(Constant.TAG,"onTimeOut");
                gotoMainActivity();
            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow");
            }

            @Override
            public void onAdCanceled() {
                Log.d(Constant.TAG,"onAdCanceled");
                gotoMainActivity();
            }

            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked");
            }
        });

    }

    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

