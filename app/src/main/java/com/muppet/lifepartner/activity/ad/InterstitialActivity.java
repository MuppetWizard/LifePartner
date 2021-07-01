package com.muppet.lifepartner.activity.ad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mbridge.msdk.MBridgeConstans;
import com.mbridge.msdk.interstitialvideo.out.InterstitialVideoListener;
import com.mbridge.msdk.interstitialvideo.out.MBInterstitialVideoHandler;
import com.mbridge.msdk.out.AutoPlayMode;
import com.mbridge.msdk.out.MBMultiStateEnum;
import com.mbridge.msdk.out.MBNativeAdvancedHandler;
import com.mbridge.msdk.out.NativeAdvancedAdListener;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.qq.e.comm.util.AdError;
import com.youyi.yesdk.ad.InterstitialAd;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.listener.InterstitialAdListener;
import com.youyi.yesdk.listener.InterstitialMediaListener;
import com.youyi.yesdk.listener.UEConfirmCallBack;
import com.youyi.yesdk.listener.UEDownloadConfirmListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import mobi.oneway.export.Ad.OWInterstitialImageAd;
import mobi.oneway.export.AdListener.OWInterstitialImageAdListener;
import mobi.oneway.export.enums.OnewayAdCloseType;
import mobi.oneway.export.enums.OnewaySdkError;

public class InterstitialActivity extends AppCompatActivity {

    private InterstitialAd interstitialAd;

//    private com.baidu.mobads.InterstitialAd baiDuInterstitial;
    private MBInterstitialVideoHandler mbInterstitialVideoHandler;
    private MBNativeAdvancedHandler mbNativeAdvancedHandler;
    private OWInterstitialImageAd owInterstitialImageAd;


    private FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        ButterKnife.bind(this);
        initStatusBar();
        bindView(R.id.btn_vertical_cha);
        bindView(R.id.btn_horizontal_cha);
        bindView(R.id.btn_bd_cha);
        bindView(R.id.btn_mb_cha);
        bindView(R.id.btn_mb_autoRender);
        bindView(R.id.btn_ow_interstitial);
        flContainer = findViewById(R.id.fl_container);
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
//                        loadBaiduChaping("7528546");
                        break;
                    case R.id.btn_mb_cha:
//                        loadMBChaping("296503","474882");
                        //debug
                        loadMBChaping("138781","146869");
                        break;
                    case R.id.btn_mb_autoRender:
                        try {
                            loadMBNativeChaping("296821","475735");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.btn_ow_interstitial:
                        loadOWInterstitialImage("FO1X8HSBTRP6ROA1");
                        break;
                }
            }
        });
    }

    private void loadOWInterstitialImage(String id) {
        owInterstitialImageAd = new OWInterstitialImageAd(this, id, new OWInterstitialImageAdListener() {
            @Override
            public void onAdReady() {
                Log.d(Constant.TAG,"onAdReady");
                if (owInterstitialImageAd.isReady()) {
                    owInterstitialImageAd.show(InterstitialActivity.this);
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
                Log.d(Constant.TAG,"onAdClose");
            }

            @Override
            public void onAdFinish(String s, OnewayAdCloseType onewayAdCloseType, String s1) {
                Log.d(Constant.TAG,"onAdFinish");
            }

            @Override
            public void onSdkError(OnewaySdkError onewaySdkError, String s) {
                Log.d(Constant.TAG,"onSdkError");
            }
        });
        owInterstitialImageAd.loadAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mbNativeAdvancedHandler != null) {
            mbNativeAdvancedHandler.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mbNativeAdvancedHandler != null)
        mbNativeAdvancedHandler.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        if(mbNativeAdvancedHandler != null)
            mbNativeAdvancedHandler.release();
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        RelativeLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }



    private void loadMBChaping(String placementId, String unit) {
        mbInterstitialVideoHandler = new MBInterstitialVideoHandler(this,placementId,unit);
        mbInterstitialVideoHandler.setInterstitialVideoListener(new InterstitialVideoListener() {
            @Override
            public void onLoadSuccess(String placementId, String unitId) {
                Log.d(Constant.TAG,"onLoadSuccess "+placementId+" : "+ unitId);
            }

            @Override
            public void onVideoLoadSuccess(String s, String s1) {
                Log.d(Constant.TAG,"onVideoLoadSuccess "+s+" : "+ s1);
                if (mbInterstitialVideoHandler.isReady()) {
                    mbInterstitialVideoHandler.show();
                }
            }

            @Override
            public void onVideoLoadFail(String s) {
                Log.d(Constant.TAG,"onVideoLoadFail "+s);
            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow ");
            }

            @Override
            public void onAdClose(boolean b) {
                Log.d(Constant.TAG,"onAdClose ");
            }

            @Override
            public void onShowFail(String s) {
                Log.d(Constant.TAG,"onShowFail ");
            }

            @Override
            public void onVideoAdClicked(String s, String s1) {
                Log.d(Constant.TAG,"onVideoAdClicked "+s+" : "+ s1);
            }

            @Override
            public void onVideoComplete(String s, String s1) {
                Log.d(Constant.TAG,"onVideoComplete "+s+" : "+ s1);
            }

            @Override
            public void onAdCloseWithIVReward(boolean b, int i) {
                Log.d(Constant.TAG,"onAdCloseWithIVReward ");
            }

            @Override
            public void onEndcardShow(String s, String s1) {
                Log.d(Constant.TAG,"onEndcardShow "+s+" : "+ s1);
            }
        });

        mbInterstitialVideoHandler.load();

    }

    private void loadMBNativeChaping(String placementId, String unitId) throws JSONException {
        ViewGroup mbNativeAdvancedView;
//        mbNativeAdvancedHandler = new MBNativeAdvancedHandler(this,placementId,unitId);
        String style = "{\n" +
                "\t\"list\": [{\n" +
                "\t\t\"target\": \"title\",\n" +
                "\t\t\"values\": {\n" +
                "\t\t\t\"paddingLeft\": 15,\n" +
                "\t\t\t\"backgroundColor\": \"yellow\",\n" +
                "\t\t\t\"fontSize\": 15,\n" +
                "\t\t\t\"fontFamily\": \"微软雅黑\",\n" +
                "\t\t\t\"color\": \"red\"\n" +
                "\t\t}\n" +
                "\t}, {\n" +
                "\t\t\"target\": \"mediaContent\",\n" +
                "\t\t\"values\": {\n" +
                "\t\t\t\"paddingTop\": 10,\n" +
                "\t\t\t\"paddingRight\": 10,\n" +
                "\t\t\t\"paddingBottom\": 10,\n" +
                "\t\t\t\"paddingLeft\": 10,\n" +
                "\t\t}\n" +
                "\t}]\n" +
                "}";

        JSONObject jsonObject = new JSONObject(style);
        mbNativeAdvancedHandler.setViewElementStyle(jsonObject);
        mbNativeAdvancedHandler.setNativeViewSize(320,250);
        mbNativeAdvancedHandler.setPlayMuteState(MBridgeConstans.REWARD_VIDEO_PLAY_NOT_MUTE);
        mbNativeAdvancedHandler.setCloseButtonState(MBMultiStateEnum.negative);
        mbNativeAdvancedHandler.autoLoopPlay(AutoPlayMode.PLAY_WHEN_NETWORK_IS_WIFI);
        mbNativeAdvancedHandler.setAdListener(new NativeAdvancedAdListener() {
            @Override
            public void onLoadFailed(String msg) {
                Log.d(Constant.TAG,"onLoadFailed "+msg);
            }

            @Override
            public void onLoadSuccessed() {
                Log.d(Constant.TAG,"onLoadSuccessed ");
            }

            @Override
            public void onLogImpression() {
                Log.d(Constant.TAG,"onLonLogImpressionoadFailed ");
            }

            @Override
            public void onClick() {
                Log.d(Constant.TAG,"onClick ");
            }

            @Override
            public void onLeaveApp() {
                Log.d(Constant.TAG,"onLeaveApp ");
            }

            @Override
            public void showFullScreen() {
                Log.d(Constant.TAG,"showFullScreen ");
            }

            @Override
            public void closeFullScreen() {
                Log.d(Constant.TAG,"closeFullScreen ");
            }

            @Override
            public void onClose() {
                Log.d(Constant.TAG,"onClose ");
            }
        });

        mbNativeAdvancedHandler.load();
        if (mbNativeAdvancedHandler.isReady()) {
            mbNativeAdvancedView = mbNativeAdvancedHandler.getAdViewGroup();
            flContainer.addView(mbNativeAdvancedView);
        }
    }

/*    private void loadBaiduChaping(String id) {
        baiDuInterstitial = new com.baidu.mobads.InterstitialAd(this,id);
        baiDuInterstitial.setListener(baiDuListener());
        baiDuInterstitial.loadAd();
    }

    private com.baidu.mobads.InterstitialAdListener baiDuListener() {
        return new com.baidu.mobads.InterstitialAdListener() {
            @Override
            public void onAdReady() {
                Log.d(Constant.TAG,"onAdReady");
                if (baiDuInterstitial.isAdReady()) {
                    baiDuInterstitial.showAd(InterstitialActivity.this);
                }
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
    }*/

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
        interstitialAd.loadInterstitialAd( new InterstitialAdListener() {

            @Override
            public void onVideoComplete() {
                Log.e(Constant.TAG,"onVideoComplete");
            }

            @Override
            public void onAdCached() {
                Log.e(Constant.TAG,"onAdCached");
                interstitialAd.show();
                setDownloadListener(interstitialAd);
            }

            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.e(Constant.TAG,"code:"+integer+" msg: "+ s);
            }

            @Override
            public void onAdLoaded() {
                Log.e(Constant.TAG,"onAdLoaded");
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