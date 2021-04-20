package com.muppet.lifepartner.activity.ad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.youyi.yesdk.ad.RewardVideoAd;
import com.youyi.yesdk.business.UEAdManager;
import com.youyi.yesdk.business.YOUEAdConstants;
import com.youyi.yesdk.listener.RewardListener;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RewardVideoActivity extends AppCompatActivity {

    private String codeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);
        initStatusBar();
        initView();
        getExtraInfo();
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

    private void initView() {
        findViewById(R.id.btn_reward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRewardVideo(codeId);
            }
        });
    }

    private void getExtraInfo() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        codeId = intent.getStringExtra("id");
    }

    private void loadRewardVideo(String id) {
        RewardVideoAd ad = new RewardVideoAd();
        ad.setRewardConfig(this,new UEAdManager()
//                .setUserID("youe_TEST")
//                .setCustomData("youe_data")
                        .setExpressViewAcceptedSize(500f,500f)
                        .setScenes(YOUEAdConstants.RitScenes.CUSTOMIZE_SCENES,"scenes_test")
                        .setOrientation(YOUEAdConstants.HORIZONTAL)
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
}