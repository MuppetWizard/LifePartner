package com.muppet.lifepartner.activity.ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActStart;
import com.muppet.lifepartner.util.StatusUtils;

public class SplashAdActivity extends AppCompatActivity {

    /*private AppOpenAd appOpenAd = null;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;*/
    private static boolean isShowingAd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);
        initStatusBar();
        initView();

    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

    private void initView() {
        bindItem(R.id.btn_my_splash);
        //正式
//        bindItem(R.id.btn_google_splash,"ca-app-pub-6865974081341189/9660712842");
        //测试
        bindItem(R.id.btn_google_splash);
        bindItem(R.id.btn_baidu_splash);
        bindItem(R.id.btn_mb_splash);
        bindItem(R.id.btn_imb_splash);
        bindItem(R.id.btn_ow_splash);
    }

    private void bindItem(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.btn_my_splash:
                        intent = new Intent(SplashAdActivity.this,ActStart.class);
                        intent.putExtra("splash",100);
                        startActivity(intent);
                        break;
                    case R.id.btn_google_splash:
//                        fetchAd("ca-app-pub-3940256099942544/3419835294");
                        break;
                    case R.id.btn_baidu_splash:
                        intent = new Intent(SplashAdActivity.this, ActStart.class);
                        intent.putExtra("splash",200);
                        startActivity(intent);
                        break;
                    case R.id.btn_mb_splash:
                        intent = new Intent(SplashAdActivity.this, ActStart.class);
                        intent.putExtra("splash",300);
                        startActivity(intent);
                        break;
                    case R.id.btn_imb_splash:
                        intent = new Intent(SplashAdActivity.this,ActStart.class);
                        intent.putExtra("splash",400);
                        startActivity(intent);
                        break;
                    case R.id.btn_ow_splash:
                        intent = new Intent(SplashAdActivity.this,ActStart.class);
                        intent.putExtra("splash",500);
                        startActivity(intent);
                        break;
                }
            }
        });
    }



    /*Google*/
/*    *//** Shows the ad if one isn't already showing. *//*
    private void showAdIfAvailable(String id) {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {
            Log.d(Constant.TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            Log.d(Constant.TAG, "onAdDismissedFullScreenContent");
                            appOpenAd = null;
                            isShowingAd = false;
                            fetchAd(id);
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            Log.d(Constant.TAG, "code: "+ adError.getCode() +
                                    "message: " + adError.getMessage() +
                                    "Cause: " + adError.getCause());
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                            Log.d(Constant.TAG, "onAdShowedFullScreenContent");
                        }
                    };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(this);

        } else {
            Log.d(Constant.TAG, "Can not show ad.");
            fetchAd(id);
        }
    }
    private void fetchAd(String id) {
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                appOpenAd = ad;
                showAdIfAvailable(id);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
                Log.e(Constant.TAG, loadAdError.getResponseInfo().toString());

            }
        };
        AdRequest request = getAdRequest();
        AppOpenAd.load(this,id,request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,loadCallback);
    }
    *//** Creates and returns ad request. *//*
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }
    *//** Utility method that checks if ad exists and can be shown. *//*
    public boolean isAdAvailable() {
        return appOpenAd != null;
    }*/
}