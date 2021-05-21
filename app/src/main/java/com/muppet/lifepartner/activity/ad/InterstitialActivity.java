package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.comm.util.AdError;
import com.youyi.yesdk.ad.InterstitialAd;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.listener.InterstitialAdListener;
import com.youyi.yesdk.listener.InterstitialMediaListener;
import com.youyi.yesdk.listener.UEConfirmCallBack;
import com.youyi.yesdk.listener.UEDownloadConfirmListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InterstitialActivity extends AppCompatActivity {

    private InterstitialAd interstitialAd;

    private com.baidu.mobads.InterstitialAd baiDuInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        ButterKnife.bind(this);
        initStatusBar();
        bindView(R.id.btn_vertical_cha);
        bindView(R.id.btn_horizontal_cha);
        bindView(R.id.btn_bd_cha);
    }

    private void bindView(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_vertical_cha:
                        loadInterstitial("0000000041",true);
                        break;
                    case R.id.btn_horizontal_cha:
                        loadInterstitial("0000000112",false);
                        break;
                    case R.id.btn_bd_cha:
                        loadBaiduChaping("7528546");
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }

    private void loadBaiduChaping(String id) {
        baiDuInterstitial = new com.baidu.mobads.InterstitialAd(this,id);
        baiDuInterstitial.setListener(baiDuListener());
        baiDuInterstitial.loadAd();
        if (baiDuInterstitial.isAdReady()) {
        }

    }

    private com.baidu.mobads.InterstitialAdListener baiDuListener() {
        return new com.baidu.mobads.InterstitialAdListener() {
            @Override
            public void onAdReady() {
                Log.d(Constant.TAG,"onAdReady");
                baiDuInterstitial.showAd(InterstitialActivity.this);
            }

            @Override
            public void onAdPresent() {
                Log.d(Constant.TAG,"onAdPresent");
            }

            @Override
            public void onAdClick(com.baidu.mobads.InterstitialAd interstitialAd) {
                Log.d(Constant.TAG,"onAdClick");
            }

            @Override
            public void onAdDismissed() {
                Log.d(Constant.TAG,"onAdDismissed");
            }

            @Override
            public void onAdFailed(String s) {
                Log.d(Constant.TAG,"onAdFailed");
            }
        };
    }

    private void loadInterstitial(String id,boolean vertical) {
        interstitialAd = new InterstitialAd();
//        interstitialAd.setInterstitialAdConfig(this,id,vertical,5,61);
        interstitialAd.setInterstitialAdConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .isVertical(vertical)
                        .setMinVideoDuration(5)
                        .setMaxVideoDuration(61)
                        .build());
        VideoOption option = new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS)
                .setAutoPlayMuted(false)
                .setDetailPageMuted(false)
                .build();
        interstitialAd.loadInterstitialAd(option, new InterstitialAdListener() {

            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.e(Constant.TAG,"code:"+integer+" msg: "+ s);
            }

            @Override
            public void onAdLoaded() {
                Log.e(Constant.TAG,"onAdLoaded");
                interstitialAd.show();
                setDownloadListener(interstitialAd);
            }

            @Override
            public void onAdShow() {
                Log.e(Constant.TAG,"onAdShow");
            }

            @Override
            public void onAdClosed() {
                Log.e(Constant.TAG,"onAdClosed");
            }

            @Override
            public void onAdClicked() {
                Log.e(Constant.TAG,"onAdClicked");
            }
        });

        interstitialAd.setMediaListener(new InterstitialMediaListener() {
            @Override
            public void onVideoInit() {
                Log.e(Constant.TAG,"onVideoInit");
            }

            @Override
            public void onVideoLoading() {
                Log.e(Constant.TAG,"onVideoLoading");
            }

            @Override
            public void onVideoReady(long l) {
                Log.e(Constant.TAG,"onVideoReady"+l);
            }

            @Override
            public void onVideoStart() {
                Log.e(Constant.TAG,"onVideoStart");
            }

            @Override
            public void onVideoPause() {
                Log.e(Constant.TAG,"onVideoPause");
            }

            @Override
            public void onVideoComplete() {
                Log.e(Constant.TAG,"onVideoComplete");
            }

            @Override
            public void onVideoError(AdError adError) {
                Log.e(Constant.TAG,
                        "onVideoError:"+adError.getErrorCode()+" msg:"+adError.getErrorMsg());
            }
            @Override
            public void onVideoPageOpen() {
                Log.e(Constant.TAG,"onVideoPageOpen");
            }
            @Override
            public void onVideoPageClose() {
                Log.e(Constant.TAG,"onVideoPageClose");
            }
        });
    }

    private void setDownloadListener(InterstitialAd ad) {
        ad.setDownloadConfirmListener(new UEDownloadConfirmListener() {
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
}