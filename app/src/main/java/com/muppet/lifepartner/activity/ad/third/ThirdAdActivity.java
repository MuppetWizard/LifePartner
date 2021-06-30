package com.muppet.lifepartner.activity.ad.third;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ad.ActAdMain;
import com.muppet.lifepartner.activity.ad.BannerActivity;
import com.muppet.lifepartner.activity.ad.DrawStreamAdActivity;
import com.muppet.lifepartner.activity.ad.FullscreenVideoActivity;
import com.muppet.lifepartner.activity.ad.InterstitialActivity;
import com.muppet.lifepartner.activity.ad.RewardVideoActivity;
import com.muppet.lifepartner.activity.ad.SplashAdActivity;
import com.muppet.lifepartner.activity.ad.StreamAdActivity;
import com.muppet.lifepartner.activity.ad.others.OtherActivity;
import com.muppet.lifepartner.activity.ad.third.baidu.BaiduAdActivity;
import com.muppet.lifepartner.activity.ad.third.mtg.MintegralAdActivity;
import com.muppet.lifepartner.activity.ad.third.ow.OWActivity;
import com.muppet.lifepartner.util.StatusUtils;
import com.youyi.yesdk.YOUEAdSdk;

public class ThirdAdActivity extends AppCompatActivity {

    private Context context = ThirdAdActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_ad);
        initStatusBar();
        initView();
    }

    private void initView() {
        bindItem(R.id.btn_baidu, BaiduAdActivity.class);
        bindItem(R.id.btn_mtg, MintegralAdActivity.class);
        bindItem(R.id.btn_ow, OWActivity.class);
    }

    private void bindItem(@IdRes int id, final Class clz) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, clz);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }
}