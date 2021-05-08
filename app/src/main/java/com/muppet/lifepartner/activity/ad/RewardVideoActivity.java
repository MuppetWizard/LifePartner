package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsRewardVideoAd;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsVideoPlayConfig;
import com.kwad.sdk.api.SdkConfig;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.youyi.yesdk.ad.BannerAd;
import com.youyi.yesdk.ad.RewardVideoAd;
import com.youyi.yesdk.ad.YOUEAdConstants;
import com.youyi.yesdk.business.UEAdManager;
import com.youyi.yesdk.listener.RewardListener;
import com.youyi.yesdk.listener.UEConfirmCallBack;
import com.youyi.yesdk.listener.UEDownloadConfirmListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class RewardVideoActivity extends AppCompatActivity {

    private KsRewardVideoAd ksRewardVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);
        initStatusBar();
        bindView(R.id.btn_temp1_vertical_reward);
        bindView(R.id.btn_temp1_horizontal_reward);
        bindView(R.id.btn_temp2_reward);
        bindView(R.id.btn_ks_reward);

    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

    private void bindView(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_temp1_vertical_reward:
                        loadRewardVideo("0000000034",YOUEAdConstants.VERTICAL);
                        break;
                    case R.id.btn_temp1_horizontal_reward:
                        loadRewardVideo("0000000114", YOUEAdConstants.HORIZONTAL);
                        break;
                    case R.id.btn_temp2_reward:
                        loadRewardVideo("0000000033", YOUEAdConstants.VERTICAL);
                        break;
                    case R.id.btn_ks_reward:
                        loadKsReward(90009001);
                        break;
                }

            }
        });
    }

    private void loadKsReward(int id) {
        KsScene scene = new KsScene.Builder(id)
//                .screenOrientation(SdkConfig.SCREEN_ORIENTATION_LANDSCAPE)
                .build();
        KsAdSDK.getLoadManager().loadRewardVideoAd(scene, new KsLoadManager.RewardVideoAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.d(Constant.TAG,"onError:"+i+" msg: "+s);
            }

            @Override
            public void onRequestResult(int i) {
                Log.d(Constant.TAG,"onRequestResult: " +i);
            }

            @Override
            public void onRewardVideoAdLoad(@Nullable List<KsRewardVideoAd> list) {
                Log.d(Constant.TAG,"onRewardVideoAdLoad");
                if (list != null && list.size() != 0) {
                    ksRewardVideoAd = list.get(0);
                    KsVideoPlayConfig videoPlayConfig = new KsVideoPlayConfig.Builder()
                            .showLandscape(false)
                            .build();
                    ksRewardVideoAd.setRewardAdInteractionListener(bindKsRewardListener());
                    ksRewardVideoAd.showRewardVideoAd(RewardVideoActivity.this,videoPlayConfig);
                }
            }
        });

    }

    private KsRewardVideoAd.RewardAdInteractionListener bindKsRewardListener() {
        return new KsRewardVideoAd.RewardAdInteractionListener() {
            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked");
            }

            @Override
            public void onPageDismiss() {
                Log.d(Constant.TAG,"onPageDismiss");
            }

            @Override
            public void onVideoPlayError(int i, int i1) {
                Log.d(Constant.TAG,"onVideoPlayError");
            }

            @Override
            public void onVideoPlayEnd() {
                Log.d(Constant.TAG,"onVideoPlayEnd");
            }

            @Override
            public void onVideoPlayStart() {
                Log.d(Constant.TAG,"onVideoPlayStart");
            }

            @Override
            public void onRewardVerify() {
                Log.d(Constant.TAG,"onRewardVerify");
            }
        };
    }

    private void loadRewardVideo(String id, int orientation) {
        RewardVideoAd ad = new RewardVideoAd();
        ad.setRewardConfig(this,new UEAdManager()
//                .setUserID("youe_TEST")
//                .setCustomData("youe_data")
                        .setExpressViewAcceptedSize(500f,500f)
                        .setOrientation(orientation)
                        .setScenes(YOUEAdConstants.RitScenes.CUSTOMIZE_SCENES,"scenes_test")
                        .build()
        ).loadRewardVideo(id, new RewardListener() {
            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.d(Constant.TAG,"onError : code: "+ integer+" msg : "+ s);
            }

            @Override
            public void onVideoCached() {
                Log.d(Constant.TAG,"onVideoCached");
                ad.show();
                setDownloadListener(ad);
            }

            @Override
            public void onADLoaded() {
                Log.d(Constant.TAG,"onADLoaded");
            }

            @Override
            public void onADShow() {
                Log.d(Constant.TAG,"onADShow");
            }

            @Override
            public void onReward(@Nullable Boolean aBoolean, @Nullable Integer integer, @Nullable String s, @Nullable Integer integer1, @Nullable String s1, @Nullable Map<String, Object> map) {
                Log.d(Constant.TAG,"onReward " + aBoolean +" id: "+integer+" msg: "+ s+ " errorC: "+ integer+ " errorM: "+ s1);
            }

            @Override
            public void onADComplete() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onVideoBarClick() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onSKipVideo() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onClosed() {
                Log.d(Constant.TAG,"onADComplete");
            }
        });

    }

    private void setDownloadListener(RewardVideoAd ad) {
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