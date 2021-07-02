package com.muppet.lifepartner.activity.ad.third.inmobi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
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
        lister = new AdEventLister();
        adNative = new InMobiNative(this, id, lister);
        adNative.setDownloaderEnabled(true);
        adNative.load();

    }

    class AdEventLister extends NativeAdEventListener {

        @Override
        public void onAdReceived(InMobiNative inMobiNative) {
            super.onAdReceived(inMobiNative);
            Log.d(TAG,"onAdReceived");
        }

        @Override
        public void onAdLoadSucceeded(InMobiNative inMobiNative) {
            super.onAdLoadSucceeded(inMobiNative);
            Log.d(TAG,"onAdLoadSucceeded");
            if (inMobiNative.isReady()) {
//                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(container.getWidth(),100 );
                container.addView(
                        inMobiNative.getPrimaryViewOfWidth(
                                context,
                                container,
                                container,
                                container.getWidth()
                        )

                );
                inMobiNative.getPrimaryViewOfWidthAndHeight(
                        context,
                        container,container,container.getWidth(),container.getHeight());
            }
        }

        @Override
        public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus status) {
            super.onAdLoadFailed(inMobiNative, status);
            Log.d(TAG,"onAdLoadFailed: "+status.getMessage()+" code: "+status.getStatusCode());
        }

        @Override
        public void onAdFullScreenDismissed(InMobiNative inMobiNative) {
            super.onAdFullScreenDismissed(inMobiNative);
            Log.d(TAG,"onAdFullScreenDismissed");
        }

        @Override
        public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {
            super.onAdFullScreenWillDisplay(inMobiNative);
            Log.d(TAG,"onAdFullScreenWillDisplay");
        }

        @Override
        public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {
            super.onAdFullScreenDisplayed(inMobiNative);
            Log.d(TAG,"onAdFullScreenDisplayed");
        }

        @Override
        public void onUserWillLeaveApplication(InMobiNative inMobiNative) {
            super.onUserWillLeaveApplication(inMobiNative);
            Log.d(TAG,"onUserWillLeaveApplication");
        }

        @Override
        public void onAdImpressed(InMobiNative inMobiNative) {
            super.onAdImpressed(inMobiNative);
            Log.d(TAG,"onAdImpressed");
        }

        @Override
        public void onAdClicked(InMobiNative inMobiNative) {
            super.onAdClicked(inMobiNative);
            Log.d(TAG,"onAdClicked");
        }

        @Override
        public void onAdStatusChanged(InMobiNative inMobiNative) {
            super.onAdStatusChanged(inMobiNative);
            Log.d(TAG,"onAdStatusChanged");
        }

        @Override
        public void onRequestPayloadCreated(byte[] bytes) {
            super.onRequestPayloadCreated(bytes);
            Log.d(TAG,"onRequestPayloadCreated");
        }

        @Override
        public void onRequestPayloadCreationFailed(InMobiAdRequestStatus status) {
            super.onRequestPayloadCreationFailed(status);
            Log.d(TAG,"onRequestPayloadCreationFailed: "+status.getMessage()+" code: "+status.getStatusCode());
        }
    }

    @SuppressLint("SetTextI18n")
    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        FrameLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);

    }
}