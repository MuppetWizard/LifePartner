package com.muppet.lifepartner.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsSplashScreenAd;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ad.SplashVPlusAd;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.CookieUtil;
import com.muppet.lifepartner.view.UserA;
import com.youyi.yesdk.ad.SplashAd;
import com.youyi.yesdk.listener.SplashListener;

public class ActStart extends AppCompatActivity{

    private int mCount;

    private SplashAd splashAd;
    private FrameLayout flSplash;

    private boolean mIsPaused;
    private boolean mGotoMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start);
        flSplash = findViewById(R.id.fl_splash);
        mCount = (int) CookieUtil.get("isFirst",0);
        if (mCount == 0) {
            UserA dialog = new UserA(this);
            dialog.show();
        }else {
            loadSplash();
//            loadKsSplashAd();
        }
    }

    private void loadKsSplashAd() {
        KsScene scene = new KsScene.Builder(4000000042L).build();
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
                SplashVPlusAd.ksSplashScreenAd = ksSplashScreenAd;
                addFragment(ksSplashScreenAd);
                flSplash.removeAllViews();
//                flSplash.addView(ksSplashScreenAd.getView());
            }
        });

    }

    private void addFragment(KsSplashScreenAd splashScreenAd) {
        Fragment fragment =
                splashScreenAd.getFragment(new KsSplashScreenAd.SplashScreenAdInteractionListener() {
                    @Override
                    public void onAdClicked() {
                        Log.d(Constant.TAG,"onAdClicked" );
                        //onAdClick 会吊起h5或者应用商店。 不直接跳转，等返回后再跳转。
                        mGotoMainActivity = true;
                        //点击不出发显示miniWindow
                        SplashVPlusAd.ksSplashScreenAd = null;
                    }

                    @Override
                    public void onAdShowError(int code, String extra) {
                        Log.e(Constant.TAG,"开屏广告显示错误 " + code + " extra " + extra);
                        //点击不出发显示miniWindow
                        SplashVPlusAd.ksSplashScreenAd = null;

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
                });
        if (!isFinishing()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_splash, fragment)
                    .commitAllowingStateLoss();
        }
    }

    private void loadSplash() {
        splashAd = new SplashAd();
        splashAd.setSplashConfig(this, "0000000032", false, 3500);
        splashAd.loadSplashAd(flSplash, new SplashListener() {
            @Override
            public void onError(Integer integer, String s) {
                Log.d(Constant.TAG,"code: "+integer+" msg: "+s );
                Toast.makeText(ActStart.this,"code: "+integer+" msg: "+s,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActStart.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onTimeOut() {
                Log.d(Constant.TAG,"onTimeOut");
                Intent intent = new Intent(ActStart.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow");
            }

            @Override
            public void onAdCanceled() {
                Log.d(Constant.TAG,"onAdCanceled");
                Intent intent = new Intent(ActStart.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked");
            }
        });

    }

    private void gotoMainActivity() {
        if (mIsPaused) {
            mGotoMainActivity = true;
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}

