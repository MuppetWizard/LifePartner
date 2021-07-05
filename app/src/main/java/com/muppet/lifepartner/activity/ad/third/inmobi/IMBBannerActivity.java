package com.muppet.lifepartner.activity.ad.third.inmobi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.inmobi.ads.InMobiNative;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;

public class IMBBannerActivity extends AppCompatActivity {

    private FrameLayout container;

    private String TAG = Constant.TAG;
    InMobiNative adNative;
    AdEventLister lister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imb_banner);
        initStatusBar();
        container = findViewById(R.id.fl_banner_container);
        loadInMobBanner(2000000000001611L);
    }

    private void loadInMobBanner(long id) {
        Log.e(TAG, "loadInMobSplash: " );
//        adListener = new NativeAdListener(this,container);
        lister = new AdEventLister(this,container);
        adNative = new InMobiNative(this, id, lister);
        adNative.setDownloaderEnabled(true);
        adNative.load();

    }

    @SuppressLint("SetTextI18n")
    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);

    }
}