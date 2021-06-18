package com.muppet.lifepartner.activity.ad;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.util.UIUtils;
import com.youyi.yesdk.ad.DrawStreamAd;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.listener.StreamAdExpress;
import com.youyi.yesdk.listener.StreamAdListener;
import com.youyi.yesdk.listener.UEVideoListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.bytedance.sdk.openadsdk.*;

public class DrawStreamAdActivity extends AppCompatActivity {

    @BindView(R.id.fl_ad_container)
    FrameLayout flAdContainer;

    private DrawStreamAd drawStreamAd;
    private StreamAdExpress adInfo;
    private List<StreamAdExpress> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_stream_ad);
        ButterKnife.bind(this);
        initStatusBar();
        loadDrawStreamAd("0000000063");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adInfo != null) {
            flAdContainer.removeAllViews();
            adInfo.destroy();
        }
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        RelativeLayout rlTop = findViewById(R.id.main_top);
        rlTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

        private void loadDrawStreamAd(String id) {
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        float expressViewHeight = UIUtils.getHeight(this);
        drawStreamAd = new DrawStreamAd();
        drawStreamAd.setDrawStreamConfig(this,
                new AdPlacement.Builder()
                        .setAdId(id)
                        .setAdCount(3)
                        .setCanInterruptVideoPlay(true)
                        .setExpressViewAcceptedSize(expressViewWidth,expressViewHeight)
                        .build());
        drawStreamAd.loadDrawStreamAd(new StreamAdListener() {
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
      /*  ad.setStreamAdInteractionListener(new StreamAdInteractionListener() {

            @Override
            public void onAdClosed() {
                flAdContainer.removeAllViews();
            }

            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked ");

            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow ");
            }

            @Override
            public void onRenderSuccess() {
                Log.d(Constant.TAG,"onRenderSuccess ");
                flAdContainer.removeAllViews();
                flAdContainer.addView(ad.getStreamView());
            }
            @Override
            public void onRenderFailed(@Nullable Integer integer, @Nullable String s) {
                Log.d(Constant.TAG,"onRenderFailed: "+ integer + " msg:"+s);
            }

        });*/
        ad.setStreamVideoAdListener(new UEVideoListener() {
            @Override
            public void onVideoAdLoad() {
                Log.d(Constant.TAG,"onVideoAdLoad ");
            }

            @Override
            public void onVideoAdError(int i, int i1) {
                Log.d(Constant.TAG,"onVideoAdError: errorCode: "+i+" extraCode: "+i1);
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
            public void onVideoAdContinuePlay() {
                Log.d(Constant.TAG,"onVideoAdContinuePlay ");
            }

            @Override
            public void onProgressUpdate(long current, long duration) {
                Log.d(Constant.TAG,"onProgressUpdate current:" +current + " duration: " +duration);
            }

            @Override
            public void onVideoAdComplete() {
                Log.d(Constant.TAG,"onVideoAdComplete ");
            }

            @Override
            public void onClickRetry() {
                Log.d(Constant.TAG,"onClickRetry ");
            }
        });
    }
}