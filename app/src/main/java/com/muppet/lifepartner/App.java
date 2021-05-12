package com.muppet.lifepartner;

import android.app.AppOpsManager;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.inmobi.sdk.InMobiSdk;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.SdkConfig;
import com.muppet.lifepartner.activity.ad.AdMob.AppOpenManager;
import com.muppet.lifepartner.util.Constant;
import com.youyi.yesdk.YOUEAdSdk;
import com.youyi.yesdk.business.YOUEAdManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.Arrays;

public class App extends Application {
    public static Application application;
//    private AppOpenManager openManager;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // 建议在Application里初始化
        x.Ext.init(this); //初始化
        x.Ext.setDebug(true); // 是否输出debug日志
        /*Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();*/
        initUEAdSdk();
//        initIMBSdk();
//        initKSAdSdk();
//        initAdmobSdk();
    }

    private void initAdmobSdk() {

    }

    private void initKSAdSdk() {
        KsAdSDK.init(application,
                new SdkConfig.Builder()
                        .appId("90009")//测试appid
                        .appName("游易")
                        .showNotification(true) //是否展示下载通知栏
                        .debug(false)
                        .build());
    }

    private void initIMBSdk() {
        //inmobi
//        JSONObject consentObject = new JSONObject();
//        try {
//            // Provide correct consent value to sdk which is obtained by User
//            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        /*InMobiSdk.init(this, "702d9239280b4cbaa460c872fd95d0c6", consentObject,new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@androidx.annotation.Nullable Error error) {
                if (null != error) {
                    Log.e(Constant.TAG, "InMobi Init failed -" + error.getMessage());
                } else {
                    Log.d(Constant.TAG, "InMobi Init Successful");
                }
            }
        });*/
        InMobiSdk.init(this,"asdf48asd7fas4f8e78fasf");
//        openManager = new AppOpenManager(this);

    }

    private void initUEAdSdk() {
        YOUEAdSdk.INSTANCE.initSDK(application,
                new YOUEAdManager()
                        .appId("000012")
                        .appName("游易-测试")
                        .deBug(true)
                        .setChannel(10)
                        .supportMultiProcess(false)
                        .build()
        );
        YOUEAdSdk.INSTANCE.getSDKVersion();
    }

    //获取项目上下文
    public static Application getAppContext() {
        return application;
    }
}
