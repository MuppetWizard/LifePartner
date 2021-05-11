package com.muppet.lifepartner.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.listener.SplashListener;
import com.youyi.yesdk.listener.UEConfirmCallBack;
import com.youyi.yesdk.listener.UEDownloadConfirmListener;

import org.jetbrains.annotations.NotNull;

public class ActStart extends AppCompatActivity{

    private int mCount;

    private SplashAd splashAd;
    private FrameLayout flSplash;

    private boolean canJump = false;


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

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            gotoMainActivity();
        }
        canJump =true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
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
//        splashAd.setSplashConfig(this, id, false, 3500);
        splashAd.setSplashConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .isCustomSkip(false).setTimeOut(3500).build());
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
                splashAd.setDownloadConfirmListener(new UEDownloadConfirmListener() {
                    @Override
                    public void onDownloadConfirm(@Nullable Activity activity, @NotNull UEConfirmCallBack ueConfirmCallBack) {
                        Log.e(Constant.TAG,"onDownloadConfirm");

                        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                        alert.setMessage("确认开始下载应用");
                        alert.setCancelable(false);
                        alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ueConfirmCallBack.onConfirm();
                            }
                        });
                        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ueConfirmCallBack.onCancel();
                            }
                        });

                        AlertDialog dialog = alert.create();
                        dialog.show();
                    }
                });
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
        if (canJump) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            canJump = true;
        }
    }
}

