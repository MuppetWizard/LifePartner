package com.muppet.lifepartner.activity.ad;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.StatusUtils;
import com.youyi.yesdk.ad.BannerAd;

public class ActAdMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_main);
        initStatusBar();
        initView();
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);

        TextView tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(getText(R.string.main_sdk_version));
    }

    private void initView() {
        bindItem(R.id.btn_splash, SplashAdActivity.class);
        bindItem(R.id.btn_banner, BannerActivity.class);
        bindItem(R.id.btn_interstitial, InterstitialActivity.class);
        bindItem(R.id.btn_full_screen_video, FullscreenVideoActivity.class);
        bindItem(R.id.btn_csj_reward, RewardVideoActivity.class);
        bindItem(R.id.btn_gdt_reward, RewardVideoActivity.class);
        bindItem(R.id.btn_stream, StreamAdActivity.class);
        bindItem(R.id.btn_draw_stream, DrawStreamAdActivity.class);

    }

    private void bindItem(@IdRes int id, final Class clz) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActAdMain.this, clz);
                //穿山甲激励视频
                if (v.getId() == R.id.btn_csj_reward) {
                    intent.putExtra("id","0000000034");
                }
                //广点通激励视频
                if (v.getId() == R.id.btn_gdt_reward) {
                    intent.putExtra("id","0000000033");
                }
                startActivity(intent);
            }
        });
    }
}