package com.muppet.lifepartner.activity.ad.third.mtg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.mbridge.msdk.out.MBSplashHandler;
import com.mbridge.msdk.out.MBSplashLoadListener;
import com.mbridge.msdk.out.MBSplashShowListener;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;

public class MTGSplashActivity extends AppCompatActivity {

    private FrameLayout flSplash;
    private MBSplashHandler mbSplashHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_t_g_splash);
        initStatusBar();
        flSplash = findViewById(R.id.fl_splash);
        //                        loadMBSplash("296049","474417");
        //测试
        loadMBSplash("173349","209547");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mbSplashHandler != null) {
            mbSplashHandler.onResume();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mbSplashHandler != null) {
            mbSplashHandler.onPause();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mbSplashHandler != null) {
            mbSplashHandler.onDestroy();
        }
    }

    private void loadMBSplash(String placementId,String unitId) {
        mbSplashHandler = new MBSplashHandler(this,placementId,unitId,true,5);
        mbSplashHandler.setLoadTimeOut(3500);
//        mbSplashHandler.setLogoView(llLogo, ViewGroup.LayoutParams.MATCH_PARENT, 500);
        mbSplashHandler.setSplashLoadListener(new MBSplashLoadListener() {
            @Override
            public void onLoadSuccessed(int i) {
                Log.d(Constant.TAG,"onLoadSuccessed");
            }

            @Override
            public void onLoadFailed(String msg, int reqType) {
                Log.d(Constant.TAG,"onLoadFailed "+msg + reqType);
                finish();
            }
        });
        mbSplashHandler.setSplashShowListener(new MBSplashShowListener() {
            @Override
            public void onShowSuccessed() {
                Log.d(Constant.TAG,"onShowSuccessed");
            }

            @Override
            public void onShowFailed(String s) {
                Log.d(Constant.TAG,"onShowFailed "+s);
                finish();
            }

            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked");
            }

            @Override
            public void onDismiss(int i) {
                Log.d(Constant.TAG,"onDismiss "+i);
                finish();
            }

            @Override
            public void onAdTick(long l) {
                Log.d(Constant.TAG,"onAdTick " + l);
            }
        });
        mbSplashHandler.loadAndShow(flSplash);
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        FrameLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }
}