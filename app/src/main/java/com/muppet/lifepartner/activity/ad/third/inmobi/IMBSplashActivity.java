package com.muppet.lifepartner.activity.ad.third.inmobi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.mbridge.msdk.out.Frame;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.youyi.yesdk.YOUEAdSdk;

public class IMBSplashActivity extends AppCompatActivity {

    private Context context = IMBSplashActivity.this;
    private String TAG = Constant.TAG;
    private FrameLayout container;

    InMobiNative adNative;
    AdEventLister lister;
    NativeAdEventListener adListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imb_splash);
        initStatusBar();
        container = findViewById(R.id.fl_container);
        loadInMobSplash(2000000000001593L);
    }

    private void loadInMobSplash(long id) {
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
        FrameLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lister = null;
    }
}