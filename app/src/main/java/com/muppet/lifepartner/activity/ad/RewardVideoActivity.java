package com.muppet.lifepartner.activity.ad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.mbridge.msdk.MBridgeConstans;
import com.mbridge.msdk.out.MBRewardVideoHandler;
import com.mbridge.msdk.out.RewardVideoListener;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.youyi.yesdk.ad.RewardVideoAd;
import com.youyi.yesdk.ad.YOUEAdConstants;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.listener.RewardListener;
import com.youyi.yesdk.listener.UEConfirmCallBack;
import com.youyi.yesdk.listener.UEDownloadConfirmListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import mobi.oneway.export.Ad.OWRewardedAd;
import mobi.oneway.export.AdListener.OWRewardedAdListener;
import mobi.oneway.export.enums.OnewayAdCloseType;
import mobi.oneway.export.enums.OnewaySdkError;

public class RewardVideoActivity extends AppCompatActivity {

    private com.baidu.mobads.rewardvideo.RewardVideoAd reWardVideo;
    private MBRewardVideoHandler mbRewardVideoHandler;

    private OWRewardedAd owRewardedAd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);
        initStatusBar();
        bindView(R.id.btn_temp1_vertical_reward);
        bindView(R.id.btn_temp1_horizontal_reward);
        bindView(R.id.btn_temp2_reward);
        bindView(R.id.btn_bd_reward);
        bindView(R.id.btn_mb_reward);
        bindView(R.id.btn_ow_reward);
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
                    case R.id.btn_bd_reward:
                        loadBaiduReward("7528547");
                        break;
                    case R.id.btn_mb_reward:
//                        loadMBReward("296580","475142");
                        //debug
                        loadMBReward("138786","146874");
                        break;
                    case R.id.btn_ow_reward:
                        loadOWReward("UN8VPSTSX2DUP7NI");
                        break;
                }

            }
        });
    }

    private void loadOWReward(String id) {

        owRewardedAd = new OWRewardedAd(this, id, new OWRewardedAdListener() {
            @Override
            public void onAdReady() {
                Log.d(Constant.TAG,"onAdReady");
                if (owRewardedAd.isReady()) {
                    owRewardedAd.show(RewardVideoActivity.this);
                }
            }

            @Override
            public void onAdShow(String s) {
                Log.d(Constant.TAG,"onAdShow");
            }

            @Override
            public void onAdClick(String s) {
                Log.d(Constant.TAG,"onAdClick");
            }

            @Override
            public void onAdClose(String s, OnewayAdCloseType onewayAdCloseType) {
                Log.d(Constant.TAG,"onAdClose"+onewayAdCloseType);
            }

            @Override
            public void onAdFinish(String tag, OnewayAdCloseType onewayAdCloseType, String sessionId) {
                Log.d(Constant.TAG,"onAdFinish"+onewayAdCloseType);
            }

            @Override
            public void onSdkError(OnewaySdkError onewaySdkError, String message) {
                Log.d(Constant.TAG,"onSdkError");
            }
        });
        owRewardedAd.loadAd();
    }

    private void loadMBReward(String placementId, String unitId) {
        mbRewardVideoHandler = new MBRewardVideoHandler(this,placementId,unitId);
        mbRewardVideoHandler.setRewardVideoListener(new RewardVideoListener() {
            @Override
            public void onVideoLoadSuccess(String placementId, String unitId) {
                Log.d(Constant.TAG,"onVideoLoadSuccess " + placementId + " : " + unitId);
                if (mbRewardVideoHandler.isReady()) {
                    mbRewardVideoHandler.show("1");
                }
            }

            @Override
            public void onLoadSuccess(String s, String s1) {
                Log.d(Constant.TAG,"onLoadSuccess " + s + " : " + s1);

            }

            @Override
            public void onVideoLoadFail(String errorMsg) {
                Log.d(Constant.TAG,"onVideoLoadFail " + errorMsg);
            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow");
            }

            @Override
            public void onAdClose(boolean isCompleteView, String rewardName, float rewardAmout) {
                Log.d(Constant.TAG,"onAdClose "+ isCompleteView + " : "+ rewardName + " : "+rewardAmout);
            }

            @Override
            public void onShowFail(String errorMsg) {
                Log.d(Constant.TAG,"onShowFail " + errorMsg );
            }

            @Override
            public void onVideoAdClicked(String s, String s1) {
                Log.d(Constant.TAG,"onVideoAdClicked " + s + " : " + s1);
            }

            @Override
            public void onVideoComplete(String s, String s1) {
                Log.d(Constant.TAG,"onVideoComplete " + s + " : " + s1);
            }

            @Override
            public void onEndcardShow(String s, String s1) {
                Log.d(Constant.TAG,"onEndcardShow " + s + " : " + s1);
            }
        });
        mbRewardVideoHandler.playVideoMute(MBridgeConstans.REWARD_VIDEO_PLAY_NOT_MUTE);
        mbRewardVideoHandler.setRewardPlus(true);
        mbRewardVideoHandler.load();
    }

    private void loadBaiduReward(String id) {
        if (reWardVideo == null) {
            reWardVideo = new com.baidu.mobads.rewardvideo.RewardVideoAd(this, id, new com.baidu.mobads.rewardvideo.RewardVideoAd.RewardVideoAdListener() {
                @Override
                public void onAdShow() {
                    Log.d(Constant.TAG,"onAdShow");
                }

                @Override
                public void onAdClick() {
                    Log.d(Constant.TAG,"onAdClick");
                }

                @Override
                public void onAdClose(float v) {
                    Log.d(Constant.TAG,"onAdClick");
                }

                @Override
                public void onAdFailed(String s) {
                    Log.d(Constant.TAG,"onAdFailed:"+s);
                    reWardVideo.load();
                }

                @Override
                public void onVideoDownloadSuccess() {
                    Log.d(Constant.TAG,"onVideoDownloadSuccess");
                    if (reWardVideo.isReady()) {
                        reWardVideo.show();
                    }
                }

                @Override
                public void onVideoDownloadFailed() {
                    Log.d(Constant.TAG,"onVideoDownloadFailed");
                }

                @Override
                public void playCompletion() {
                    Log.d(Constant.TAG,"playCompletion");
                }
            });
        }
//        reWardVideo.show();

        reWardVideo.load();

    }

    private void loadRewardVideo(String id, int orientation) {
        RewardVideoAd ad = new RewardVideoAd();
        ad.setRewardConfig(this,new AdPlacement.Builder()
                .setAdId(id)
                .setUserID("youe_TEST")
                .setCustomData("youe_data")
                .setExpressViewAcceptedSize(500f,500f)
                .setOrientation(orientation)
                .setScenes(YOUEAdConstants.RitScenes.CUSTOMIZE_SCENES,"scenes_test")
                .build()
        ).loadRewardVideo( new RewardListener() {
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
                Log.d(Constant.TAG,"onReward " + aBoolean +" id: "+integer+" msg: "+ s+ " errorC: "+ integer+ " errorM: "+ s1 + " map" + map);
            }

            @Override
            public void onADComplete() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onVideoBarClick() {
                Log.d(Constant.TAG,"onVideoBarClick");
            }

            @Override
            public void onSKipVideo() {
                Log.d(Constant.TAG,"onSKipVideo");
            }

            @Override
            public void onClosed() {
                Log.d(Constant.TAG,"onClosed");
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