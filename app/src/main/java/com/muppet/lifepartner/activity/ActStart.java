package com.muppet.lifepartner.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.inmobi.sdk.InMobiSdk;

import com.mbridge.msdk.out.MBSplashHandler;
import com.mbridge.msdk.out.MBSplashLoadListener;
import com.mbridge.msdk.out.MBSplashShowListener;
import com.muppet.lifepartner.R;
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

import java.util.HashMap;

import mobi.oneway.export.Ad.OWSplashAd;
import mobi.oneway.export.AdListener.OWSplashAdListener;
import mobi.oneway.export.enums.OnewaySdkError;


public class ActStart extends AppCompatActivity{

    private int mCount;

    private SplashAd splashAd;
//    private com.baidu.mobads.SplashAd baiduSplash;
    private FrameLayout flSplash;
    private LinearLayout llLogo;

    private OWSplashAd oWsplashAd;


    private int splashType = -1;
    private boolean canJump = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start);
        initStatusBar();
        flSplash = findViewById(R.id.fl_splash);
        llLogo = findViewById(R.id.ll_logo);

        mCount = (int) CookieUtil.get("isFirst",0);
        if (mCount == 0) {
            UserA dialog = new UserA(this);
            dialog.show();
        }else {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("splash")) {
                splashType = intent.getIntExtra("splash", -1);
                switch (splashType) {
                    case 100:
                        loadSplash("0000000032");
                        //test
//                    loadSplash("0000000225");
                        break;
                    case 200:
                    case 300:

                        break;
                    case 400:
//                        loadInMobSplash(2000000000000938L);
                        loadInMobSplash(1546731836888L);
                        break;
                    case 500:
                        loadOWSplash("EMLRUIU2CM5YIFHZ");
                        break;
                }

            }else {
                loadSplash("0000000032");
                //test
//                loadSplash("0000000225");
            }
        }
    }

    private void loadOWSplash(String id) {
        oWsplashAd = new OWSplashAd(this, id, new OWSplashAdListener() {
            @Override
            public void onAdReady() {
                Log.d(Constant.TAG,"onAdReady");
                oWsplashAd.showSplashAd(flSplash);
            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow");
            }

            @Override
            public void onAdError(OnewaySdkError onewaySdkError, String s) {
                Log.d(Constant.TAG,"onAdError: "+onewaySdkError +" msg: "+s);

            }

            @Override
            public void onAdFinish() {
                Log.d(Constant.TAG,"onAdFinish");
            }

            @Override
            public void onAdClick() {
                Log.d(Constant.TAG,"onAdClick");
            }
        },3500);
        oWsplashAd.loadSplashAd();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (baiduSplash != null) {
//            baiduSplash.destroy();
//            baiduSplash = null;
//        }
        if (oWsplashAd != null) {
            oWsplashAd.destory();
        }
        if (splashAd != null) {
            splashAd.destroy();
        }
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, false);
//        FrameLayout llTop = findViewById(R.id.top);
//        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

    private void loadSplash(String id) {

        splashAd = new SplashAd();
        splashAd.setSplashConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .setTimeOut(3500)
                        .build());
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


    private void loadInMobSplash(long id) {
        InMobiNative adNative = new InMobiNative(this, id, nativeAdEventListener());
        adNative.setDownloaderEnabled(true);
        adNative.load();
    }

    private NativeAdEventListener nativeAdEventListener() {
        return new NativeAdEventListener() {
            @Override
            public void onAdReceived(InMobiNative inMobiNative) {
                super.onAdReceived(inMobiNative);
                Log.d(Constant.TAG,"onAdReceived");
            }

            @Override
            public void onAdLoadSucceeded(InMobiNative inMobiNative) {
                super.onAdLoadSucceeded(inMobiNative);
                Log.d(Constant.TAG,"onAdLoadSucceeded");
                if (inMobiNative.isReady()) {
                    flSplash.addView(
                            inMobiNative.getPrimaryViewOfWidth(
                                    ActStart.this,
                                    flSplash,
                                    flSplash,
                                    flSplash.getWidth()
                            )
                    );
                }
            }

            @Override
            public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus status) {
                super.onAdLoadFailed(inMobiNative, status);
                Log.d(Constant.TAG,"onAdLoadFailed: "+status.getMessage()+" code: "+status.getStatusCode());
            }

            @Override
            public void onAdFullScreenDismissed(InMobiNative inMobiNative) {
                super.onAdFullScreenDismissed(inMobiNative);
                Log.d(Constant.TAG,"onAdFullScreenDismissed");
            }

            @Override
            public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {
                super.onAdFullScreenWillDisplay(inMobiNative);
                Log.d(Constant.TAG,"onAdFullScreenWillDisplay");
            }

            @Override
            public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {
                super.onAdFullScreenDisplayed(inMobiNative);
                Log.d(Constant.TAG,"onAdFullScreenDisplayed");
            }

            @Override
            public void onUserWillLeaveApplication(InMobiNative inMobiNative) {
                super.onUserWillLeaveApplication(inMobiNative);
                Log.d(Constant.TAG,"onUserWillLeaveApplication");
            }

            @Override
            public void onAdImpressed(InMobiNative inMobiNative) {
                super.onAdImpressed(inMobiNative);
                Log.d(Constant.TAG,"onAdImpressed");
            }

            @Override
            public void onAdClicked(InMobiNative inMobiNative) {
                super.onAdClicked(inMobiNative);
                Log.d(Constant.TAG,"onAdClicked");
            }

            @Override
            public void onAdStatusChanged(InMobiNative inMobiNative) {
                super.onAdStatusChanged(inMobiNative);
                Log.d(Constant.TAG,"onAdStatusChanged");
            }

            @Override
            public void onRequestPayloadCreated(byte[] bytes) {
                super.onRequestPayloadCreated(bytes);
                Log.d(Constant.TAG,"onRequestPayloadCreated");
            }

            @Override
            public void onRequestPayloadCreationFailed(InMobiAdRequestStatus status) {
                super.onRequestPayloadCreationFailed(status);
                Log.d(Constant.TAG,"onRequestPayloadCreationFailed: "+status.getMessage()+" code: "+status.getStatusCode());
            }
        };
    }

    private void gotoMainActivity() {
        if (canJump) {
            if (splashType == -1) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            finish();
            Log.d(Constant.TAG,"Jumped");
        }else {
            canJump = true;
        }
    }

   /* @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }else {
            return super.dispatchKeyEvent(event);
        }
    }*/
}

