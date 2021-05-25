package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.AdSize;
import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;
import com.baidu.mobads.rewardvideo.AbstractScreenVideoAd;
import com.baidu.mobads.rewardvideo.FullScreenVideoAd;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.util.UIUtils;
import com.youyi.yesdk.ad.BannerAd;
import com.youyi.yesdk.ad.FullVideoAd;
import com.youyi.yesdk.ad.YOUEAdConstants;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.business.UEAdManager;
import com.youyi.yesdk.listener.FullVideoListener;
import com.youyi.yesdk.listener.UEConfirmCallBack;
import com.youyi.yesdk.listener.UEDownloadConfirmListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FullscreenVideoActivity extends AppCompatActivity {

    private FullVideoAd fullVideoAd;
    private InterstitialAd mInterstitialAd;

    private RelativeLayout mVideoContainer;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);
        initStatusBar();

        mContainer = findViewById(R.id.fl_video);
        mVideoContainer = new RelativeLayout(this);
        RelativeLayout.LayoutParams reLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        reLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mContainer.addView(mVideoContainer,reLayoutParams);

        bindView(R.id.btn_vertical_video);
        bindView(R.id.btn_horizontal_video);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }

    private void bindView(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.btn_vertical_video:
                            loadFullScreenVideo("0000000046", YOUEAdConstants.VERTICAL);
                            break;
                        case R.id.btn_horizontal_video:
                            loadFullScreenVideo("0000000113",YOUEAdConstants.HORIZONTAL);
                            break;
                        case R.id.btn_bd_video:
                            loadBaiduVideo("7528545");
                    }
            }
        });
    }

    private void loadBaiduVideo(String id) {
        mInterstitialAd = new InterstitialAd(this, AdSize.InterstitialForVideoBeforePlay,id);
        mInterstitialAd.setListener(new InterstitialAdListener() {
            @Override
            public void onAdReady() {
                Log.d(Constant.TAG,"onAdReady");
            }

            @Override
            public void onAdPresent() {
                Log.d(Constant.TAG,"onAdPresent");
            }

            @Override
            public void onAdClick(InterstitialAd interstitialAd) {
                Log.d(Constant.TAG,"onAdClick");
            }

            @Override
            public void onAdDismissed() {
                Log.d(Constant.TAG,"onAdDismissed");
            }

            @Override
            public void onAdFailed(String s) {
                Log.d(Constant.TAG,"onAdFailed:"+s);
            }
        });
        mInterstitialAd.loadAdForVideoApp((int) UIUtils.getScreenWidthDp(FullscreenVideoActivity.this),500);
        if (mInterstitialAd.isAdReady()) {
            mInterstitialAd.showAdInParentForVideoApp(this,mVideoContainer);
        }
    }

    /*private void loadBaiduVideo(String id) {
        FullScreenVideoAd.setAppSid("c9f473aa");
        FullScreenVideoAd videoAd = new FullScreenVideoAd(this, id, new FullScreenVideoAd.FullScreenVideoAdListener() {
            @Override
            public void onAdShow() {
                Log.d(Constant.TAG, "onAdShow");
            }

            @Override
            public void onAdClick() {
                Log.d(Constant.TAG, "onAdClick");
            }

            @Override
            public void onAdClose(float v) {
                Log.d(Constant.TAG, "onAdClose");
            }

            @Override
            public void onAdFailed(String s) {
                Log.d(Constant.TAG, "onAdFailed");
            }

            @Override
            public void onVideoDownloadSuccess() {
                Log.d(Constant.TAG, "onVideoDownloadSuccess");
            }

            @Override
            public void onVideoDownloadFailed() {
                Log.d(Constant.TAG, "onVideoDownloadFailed");
            }

            @Override
            public void playCompletion() {
                Log.d(Constant.TAG, "playCompletion");
            }

            @Override
            public void onAdSkip(float v) {
                Log.d(Constant.TAG, "onAdSkip");
            }
        });

        videoAd.load();
        if (videoAd.isReady()) {
            videoAd.show();
        }
    }*/


    private void loadFullScreenVideo(String id, int orientation) {
        fullVideoAd = new FullVideoAd();
        fullVideoAd.setVideoConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .setExpressViewAcceptedSize(500,500)
                        .setOrientation(orientation)
                        .setMinVideoDuration(5)
                        .setMaxVideoDuration(20)
                        .build());
        fullVideoAd.loadFullVideo(new FullVideoListener() {
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
                setDownloadListener(fullVideoAd);
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
    private void setDownloadListener(FullVideoAd ad) {
        ad.setDownloadConfirmListener(new UEDownloadConfirmListener() {
            @Override
            public void onDownloadConfirm(@androidx.annotation.Nullable Activity activity, @NotNull UEConfirmCallBack ueConfirmCallBack) {
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
}