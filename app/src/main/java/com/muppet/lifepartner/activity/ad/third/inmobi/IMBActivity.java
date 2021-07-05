package com.muppet.lifepartner.activity.ad.third.inmobi;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.StatusUtils;

public class IMBActivity extends AppCompatActivity {

    private Context context = IMBActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imb);
        initStatusBar();
        bindItem(R.id.btn_imb_splash,IMBSplashActivity.class);
        bindItem(R.id.btn_imb_banner,IMBBannerActivity.class);
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