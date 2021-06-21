package com.muppet.lifepartner.activity.ad;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.muppet.lifepartner.App;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.util.UIUtils;
import com.qq.e.ads.cfg.BrowserType;
import com.qq.e.ads.nativ.express2.AdEventListener;
import com.qq.e.ads.nativ.express2.MediaEventListener;
import com.qq.e.ads.nativ.express2.NativeExpressAD2;
import com.qq.e.ads.nativ.express2.NativeExpressADData2;
import com.qq.e.ads.nativ.express2.VideoOption2;
import com.qq.e.comm.util.AdError;
import com.youyi.yesdk.ad.StreamAd;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.listener.DislikeListener;
import com.youyi.yesdk.listener.StreamAdExpress;
import com.youyi.yesdk.listener.StreamAdInteractionListener;
import com.youyi.yesdk.listener.StreamAdListener;
import com.youyi.yesdk.listener.UEConfirmCallBack;
import com.youyi.yesdk.listener.UEDownloadConfirmListener;
import com.youyi.yesdk.listener.UEVideoListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StreamAdActivity extends AppCompatActivity {

    @BindView(R.id.fl_ad)
    FrameLayout flAdView;

    private StreamAd streamAd ;
    private StreamAdExpress adInfo;
    private List<StreamAdExpress> mList;

    private NativeExpressAD2 mNativeExpressAD2;
    private NativeExpressADData2 mNativeExpressADData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_test);
        ButterKnife.bind(this);
        initStatusBar();

        bindItemClick(R.id.btn_csj_stream);
        bindItemClick(R.id.btn_gdt_stream);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adInfo != null) {
            flAdView.removeAllViews();
            adInfo.destroy();
        }
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }

    private void bindItemClick(@IdRes int view) {
        findViewById(view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (view) {
                    case R.id.btn_csj_stream:
                        loadStream("0000000058");
//                        loadStream("0000000184");
                        break;
                    case R.id.btn_gdt_stream:
                        loadGDTStream("1081890530378337");
                        break;
                }
            }
        });
    }

    private void loadGDTStream(String id) {
        mNativeExpressAD2 = new NativeExpressAD2(this,id,bindGDTListener());
        mNativeExpressAD2.setAdSize((int) UIUtils.getScreenWidthDp(this),0);
        mNativeExpressAD2.setVideoOption2(
                new VideoOption2.Builder()
                        .setAutoPlayPolicy(VideoOption2.AutoPlayPolicy.WIFI)
                        .setAutoPlayMuted(true)
                        .setDetailPageMuted(false)
                        .build());
        mNativeExpressAD2.setBrowserType(BrowserType.Inner);
        mNativeExpressAD2.loadAd(4);
    }

    private NativeExpressAD2.AdLoadListener bindGDTListener() {
        return new NativeExpressAD2.AdLoadListener() {
            @Override
            public void onLoadSuccess(List<NativeExpressADData2> list) {
                Log.d(Constant.TAG,"onLoadSuccess, size: "+list.size());
                render(list);
            }

            @Override
            public void onNoAD(AdError adError) {
                Log.d(Constant.TAG,"onNoAD:"+ adError.getErrorCode() + " msg: "+adError.getErrorMsg());
            }
        };
    }

    private void render(List<NativeExpressADData2> list) {
        mNativeExpressADData2 = list.get(0);
        mNativeExpressADData2.setAdEventListener(new AdEventListener() {
            @Override
            public void onClick() {
                Log.d(Constant.TAG,"onClick");
            }

            @Override
            public void onExposed() {
                Log.d(Constant.TAG,"onExposed");
            }

            @Override
            public void onRenderSuccess() {
                Log.d(Constant.TAG,"onRenderSuccess");
                flAdView.removeAllViews();
                if (mNativeExpressADData2.getAdView() != null) {
                    flAdView.addView(mNativeExpressADData2.getAdView());
                }
            }

            @Override
            public void onRenderFail() {
                Log.d(Constant.TAG,"onRenderFail");
            }

            @Override
            public void onAdClosed() {
                Log.d(Constant.TAG,"onAdClosed");
            }
        });
        mNativeExpressADData2.setMediaListener(new MediaEventListener() {
            @Override
            public void onVideoCache() {
                Log.d(Constant.TAG,"onVideoCache");
            }

            @Override
            public void onVideoStart() {
                Log.d(Constant.TAG,"onVideoStart");
            }

            @Override
            public void onVideoResume() {
                Log.d(Constant.TAG,"onVideoResume");
            }

            @Override
            public void onVideoPause() {
                Log.d(Constant.TAG,"onVideoPause");
            }

            @Override
            public void onVideoComplete() {
                Log.d(Constant.TAG,"onVideoComplete");
            }

            @Override
            public void onVideoError() {
                Log.d(Constant.TAG,"onVideoError");
            }
        });
        mNativeExpressADData2.render();
    }

    private void loadStream(String id) {
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        streamAd = new StreamAd();
        streamAd.setStreamConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .setExpressViewAcceptedSize(expressViewWidth, 0)
                        .setAdCount(3)
                        .build());
        streamAd.loadStreamAd(new StreamAdListener() {
            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.d(Constant.TAG,"onError : code: "+ integer+" msg : "+ s);

            }

            @Override
            public void onAdLoaded(@NotNull ArrayList<StreamAdExpress> arrayList) {
                mList = new ArrayList<>();
                mList.addAll(arrayList);
                adInfo = arrayList.get(0);
                Log.d(Constant.TAG,"onAdLoaded "+ mList.size());
                bindAdListener(adInfo);
                adInfo.render();
            }
        });

    }

    private void bindAdListener(StreamAdExpress ad) {
//        交互监听
        ad.setStreamAdInteractionListener(new  StreamAdInteractionListener() {
            @Override
            public void onAdClosed() {
                Log.d(Constant.TAG,"onAdClosed ");
                flAdView.removeAllViews();
            }

            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked ");

            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow ");
                ad.setDownloadConfirmListener(new UEDownloadConfirmListener() {
                    @Override
                    public void onDownloadConfirm(@Nullable Activity activity, @NotNull UEConfirmCallBack ueConfirmCallBack) {
                        Log.d(Constant.TAG,"onDownloadConfirm ");
                    }
                });
            }

            @Override
            public void onRenderSuccess() {
                Log.d(Constant.TAG,"onRenderSuccess ");
                flAdView.removeAllViews();
                ad.getStreamView().setBackgroundColor(App.application.getResources().getColor(R.color.app_light_blue));

                flAdView.addView(ad.getStreamView());
            }

            @Override
            public void onRenderFailed(@Nullable Integer code, @Nullable String msg) {
                Log.d(Constant.TAG,"onRenderFailed: "+ code + " msg:"+msg);
            }
        });
        //视频监听
        ad.setStreamVideoAdListener(new UEVideoListener() {

            @Override
            public void onVideoAdLoad() {
                Log.d(Constant.TAG,"onVideoAdLoad ");
            }
            @Override
            public void onVideoAdError(int errorCode, int extraCode) {
                Log.d(Constant.TAG,"onVideoAdError "+errorCode+ " :"+extraCode);
            }

            @Override
            public void onVideoAdStartPlay() {
                Log.d(Constant.TAG,"onVideoAdStartPlay ");
            }

            @Override
            public void onVideoAdPaused() {
                Log.d(Constant.TAG,"onVideoAdPaused ");
            }

            @Override
            public void onVideoAdComplete() {
                Log.d(Constant.TAG,"onVideoAdComplete ");
            }
        });

        //dislike监听
        ad.setStreamAdDislikeCallback(new DislikeListener() {
            @Override
            public void onShow() {
                Log.d(Constant.TAG,"onShow ");
            }

            @Override
            public void onSelected(int i, String s, boolean b) {
                Log.d(Constant.TAG,"onSelected ");
                flAdView.removeAllViews();
            }

            @Override
            public void onCancel() {
                Log.d(Constant.TAG,"onCancel ");
            }

        });
    }
}