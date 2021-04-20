package com.muppet.lifepartner.activity.ad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.util.UIUtils;
import com.youyi.yesdk.ad.StreamAd;
import com.youyi.yesdk.business.UEAdManager;
import com.youyi.yesdk.listener.DislikeListener;
import com.youyi.yesdk.listener.StreamAdExpress;
import com.youyi.yesdk.listener.StreamAdInteractionListener;
import com.youyi.yesdk.listener.StreamAdListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_test);
        ButterKnife.bind(this);
        initStatusBar();
        loadStream("0000000058");
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

    private void loadStream(String id) {
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        streamAd = new StreamAd();
        streamAd.setStreamConfig(this,
                new UEAdManager()
                        .setExpressViewAcceptedSize(expressViewWidth,350f)
                        .setAdCount(3)
                        .build());
        streamAd.loadStreamAd(id, new StreamAdListener() {
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
        ad.setStreamAdInteractionListener(new StreamAdInteractionListener() {
            @Override
            public void onAdClicked(@Nullable View view, int i) {
                Log.d(Constant.TAG,"onAdClicked ");

            }

            @Override
            public void onAdShow(@Nullable View view, int i) {
                Log.d(Constant.TAG,"onAdShow ");
            }

            @Override
            public void onRenderSuccess(@Nullable View view, float v, float v1) {
                Log.d(Constant.TAG,"onRenderSuccess ");
                flAdView.removeAllViews();
                flAdView.addView(view);
            }

            @Override
            public void onRenderFailed(@Nullable View view, @Nullable String s, int i) {
                Log.d(Constant.TAG,"onRenderFailed ");

            }
        });
        ad.setStreamAdDislikeCallback(new DislikeListener() {
            @Override
            public void onShow() {
                Log.d(Constant.TAG,"onShow ");
            }

            @Override
            public void onSelected(int i, String s) {
                Log.d(Constant.TAG,"onSelected ");
                flAdView.removeAllViews();
            }

            @Override
            public void onCancel() {
                Log.d(Constant.TAG,"onCancel ");
            }

            @Override
            public void onRefuse() {
                Log.d(Constant.TAG,"onRefuse ");

            }
        });
    }
}