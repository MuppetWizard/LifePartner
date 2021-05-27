package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.AppActivity;


import com.mbridge.msdk.out.BannerSize;
import com.mbridge.msdk.out.MBBannerView;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.util.UIUtils;


import com.youyi.yesdk.ad.BannerAd;
import com.youyi.yesdk.ad.SplashAd;
import com.youyi.yesdk.ad.YOUEAdConstants;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.comm.network.NetWorkUtils;
import com.youyi.yesdk.listener.BannerAdListener;
import com.youyi.yesdk.listener.SplashListener;
import com.youyi.yesdk.listener.StreamAdInteractionListener;
import com.youyi.yesdk.listener.UEConfirmCallBack;
import com.youyi.yesdk.listener.UEDownloadConfirmListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends AppCompatActivity {

    @BindView(R.id.fl_banner)
    FrameLayout flBanner;

//    @BindView(R.id.adView)
//    AdView adView;


    private View btnCancel;
    private BannerAd bannerAd;
    private AdView adView;
    private MBBannerView mbBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        initStatusBar();


        AppActivity.setActionBarColorTheme(AppActivity.ActionBarColorTheme.ACTION_BAR_WHITE_THEME);

        bindView(R.id.btn_csj_banner);
        bindView(R.id.btn_gdt_banner);
        bindView(R.id.btn_mb_banner);
        bindView(R.id.btn_imb_banner);
        bindView(R.id.btn_bd_banner);
    }

    private void bindView(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_csj_banner) {
                    loadBanner("0000000039");
                    //162
//                    loadBanner("0000000110");
                }
                if (v.getId() == R.id.btn_gdt_banner){
                    loadBanner("0000000040");
                }
                if (v.getId() == R.id.btn_bd_banner) {
                    //3:2
//                    loadBaiduBanner("7528551");
                    //20:3
                    loadBaiduBanner("7528544");
                }
                if (v.getId() == R.id.btn_mb_banner) {
                    loadMBBannerAd("138791","146879");
                }
                if (v.getId() == R.id.btn_imb_banner) {
//                    loadInMoBi(1621364045212L);
                    //测试
//                    loadInMoBi(1431977751489005L);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bannerAd != null) {
            bannerAd.destroy();
        }
        if (adView != null) {
            adView.destroy();
        }
//        if (iBanner != null) {
//            iBanner.destroy();
//        }
        if (mbBannerView != null) {
            mbBannerView = null;
        }
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }

    private void loadBanner(String id) {
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        bannerAd = new BannerAd();
        bannerAd.setBannerConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .setExpressViewAcceptedSize(expressViewWidth,320)
                        .isCarousel(false)
                        .build()
        );
        bannerAd.loadAdBanner(flBanner, new BannerAdListener() {
            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.e(Constant.TAG,"code:"+integer+" msg: "+ s);
            }

            @Override
            public void onShow() {
                Log.e(Constant.TAG,"onShow");
                setDownloadListener(bannerAd);
            }

            @Override
            public void onClosed() {
                Log.e(Constant.TAG,"onClosed");
            }

            @Override
            public void onClicked() {
                Log.e(Constant.TAG,"onClicked");
            }

            @Override
            public void onShowAdOverLay() {
                Log.e(Constant.TAG,"onShowAdOverLay");
            }

            @Override
            public void onAdCloseOverLay() {
                Log.e(Constant.TAG,"onAdCloseOverLay");
            }

            @Override
            public void onDislikeShow() {
                Log.e(Constant.TAG,"onDislikeShow");
            }

            @Override
            public void onDislikeSelected(int i, @Nullable String s) {
                Log.e(Constant.TAG,"onDislikeSelected");
                flBanner.removeAllViews();
            }

            @Override
            public void onDislikeCanceled() {
                Log.e(Constant.TAG,"onDislikeCanceled");
            }
        });

    }
    private void setDownloadListener(BannerAd ad) {
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

    private void loadBaiduBanner(String id) {
        btnCancel = getLayoutInflater().inflate(R.layout.btn_cancel,null);
        btnCancel.setVisibility(View.GONE);
        adView = new AdView(this, id);
        AdViewListener listener = new AdViewListener() {
            @Override
            public void onAdReady(AdView adView) {
                Log.d(Constant.TAG,"onAdReady");
//                flBanner.removeAllViews();
            }

            @Override
            public void onAdShow(JSONObject jsonObject) {
                Log.d(Constant.TAG,"onAdShow:"+jsonObject);
                btnCancel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClick(JSONObject jsonObject) {
                Log.d(Constant.TAG,"onAdClick:"+jsonObject);
            }

            @Override
            public void onAdFailed(String s) {
                Log.d(Constant.TAG,"onAdFailed:"+s);
            }

            @Override
            public void onAdSwitch() {
                Log.d(Constant.TAG,"onAdSwitch");
            }

            @Override
            public void onAdClose(JSONObject jsonObject) {
                Log.d(Constant.TAG,"onAdClose"+ jsonObject);
            }
        };
        adView.setListener(listener);

        flBanner.addView(adView);
        flBanner.addView(btnCancel);
        btnCancel.setOnClickListener(v -> {
//            adView.removeAllViews();
//            flBanner.removeView(btnCancel);
            listener.onAdClose(null);
            flBanner.removeAllViews();
//            adView.destroy();
        });
    }

    private void loadMBBannerAd(String placementId, String unitId) {
        mbBannerView = new MBBannerView(this);
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        mbBannerView.init(new BannerSize(BannerSize.DEV_SET_TYPE, 100,150),placementId,unitId);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,500);
        mbBannerView.setLayoutParams(params);
        mbBannerView.setAllowShowCloseBtn(true);
        mbBannerView.setRefreshTime(30);
        mbBannerView.setBannerAdListener(new com.mbridge.msdk.out.BannerAdListener() {
            @Override
            public void onLoadFailed(String s) {
                Log.d(Constant.TAG,"onLoadFailed "+ s);
            }

            @Override
            public void onLoadSuccessed() {
                Log.d(Constant.TAG,"onLoadSuccessed");
            }

            @Override
            public void onLogImpression() {
                Log.d(Constant.TAG,"onLogImpression");
            }

            @Override
            public void onClick() {
                Log.d(Constant.TAG,"onClick");
            }

            @Override
            public void onLeaveApp() {
                Log.d(Constant.TAG,"onLeaveApp");
            }

            @Override
            public void showFullScreen() {
                Log.d(Constant.TAG,"showFullScreen");
            }

            @Override
            public void closeFullScreen() {
                Log.d(Constant.TAG,"closeFullScreen");
            }

            @Override
            public void onCloseBanner() {
                Log.d(Constant.TAG,"onCloseBanner");
                flBanner.removeAllViews();
            }
        });
        mbBannerView.load();
        flBanner.addView(mbBannerView);
    }


    private int toPixelUnits(int dipUnit) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dipUnit * density);
    }
}