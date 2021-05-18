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

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ad.SplashVPlusAd;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.CookieUtil;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.view.UserA;
import com.youyi.yesdk.ad.SplashAd;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.comm.bean.BannerAdMode;
import com.youyi.yesdk.comm.bean.SplashAdMode;
import com.youyi.yesdk.comm.platform.csj.TTBanner;
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



    private void loadSplash(String id) {
        splashAd = new SplashAd();
//        splashAd.setSplashConfig(this, id, false, 3500);
        splashAd.setSplashConfig(this, id,false,3500);
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

