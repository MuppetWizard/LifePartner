package com.muppet.lifepartner.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobad.feeds.RequestParameters;
import com.baidu.mobads.SplashLpCloseListener;
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
import java.util.logging.Logger;


public class ActStart extends AppCompatActivity{

    private int mCount;

    private SplashAd splashAd;
    private com.baidu.mobads.SplashAd baiduSplash;
    private FrameLayout flSplash;
    private MBSplashHandler mbSplashHandler;

    private View skip;


    private int splashType = -1;
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
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("splash")) {
                splashType = intent.getIntExtra("splash", -1);
                if (splashType == 200) {
                    loadBaidu("7528454");
                } else if (splashType == 100) {
                    loadSplash("0000000032");
                } else if (splashType == 300) {
                    loadMBSplash("296049","474417");
                    //测试
//                    loadMBSplash("173349","209547");
                }
            }else {
                loadSplash("0000000032");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mbSplashHandler != null) {
            mbSplashHandler.onResume();
        }
        if (canJump) {
            gotoMainActivity();
        }
        canJump =true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mbSplashHandler != null) {
            mbSplashHandler.onPause();
        }
        canJump = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mbSplashHandler != null) {
            mbSplashHandler.onDestroy();
        }
        if (baiduSplash != null) {
            baiduSplash.destroy();
            baiduSplash = null;
        }
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        FrameLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

    private void loadBaidu(String id) {
        skip = getLayoutInflater().inflate(R.layout.btn_skip,null);

        TextView tvSkip = skip.findViewById(R.id.ue_tv_skip);

        skip.setVisibility(View.GONE);
        CountDownTimer timer =  new CountDownTimer(6 * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSkip.setText(String.format(getResources().getString(R.string.ue_skip),millisUntilFinished / 1000) );
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
                gotoMainActivity();
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
                gotoMainActivity();
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
                gotoMainActivity();
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

    private void loadMBSplash(String placementId,String unitId) {
        mbSplashHandler = new MBSplashHandler(placementId,unitId,true,5);
        mbSplashHandler.setLoadTimeOut(3500);
        mbSplashHandler.setSplashLoadListener(new MBSplashLoadListener() {
            @Override
            public void onLoadSuccessed(int i) {
                Log.d(Constant.TAG,"onLoadSuccessed");
            }

            @Override
            public void onLoadFailed(String msg, int reqType) {
                Log.d(Constant.TAG,"onLoadFailed "+msg + reqType);
                gotoMainActivity();
            }
        });
        mbSplashHandler.setSplashShowListener(new MBSplashShowListener() {
            @Override
            public void onShowSuccessed() {
                Log.d(Constant.TAG,"onShowSuccessed");
            }

            @Override
            public void onShowFailed(String s) {
                Log.d(Constant.TAG,"onShowFailed "+s);
                gotoMainActivity();
            }

            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked");
            }

            @Override
            public void onDismiss(int i) {
                Log.d(Constant.TAG,"onDismiss "+i);
                gotoMainActivity();
            }

            @Override
            public void onAdTick(long l) {
                Log.d(Constant.TAG,"onAdTick " + l);
            }
        });
        mbSplashHandler.loadAndShow(flSplash);
    }

    private void loadSplash(String id) {
        splashAd = new SplashAd();
//        splashAd.setSplashConfig(this, id, false, 3500);
//        splashAd.setSplashConfig(this, id,false,3500);
        splashAd.setSplashConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .isCustomSkip(false)
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
}

