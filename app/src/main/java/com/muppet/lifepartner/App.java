package com.muppet.lifepartner;

import android.app.AppOpsManager;
import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.muppet.lifepartner.activity.ad.AdMob.AppOpenManager;
import com.youyi.yesdk.YOUEAdSdk;
import com.youyi.yesdk.business.YOUEAdManager;

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
        YOUEAdSdk.INSTANCE.initSDK(application,
                new YOUEAdManager()
                        .appId("000012")
                        .appName("游易-测试")
                        .deBug(false)
                        .setChannel(10)
                        .supportMultiProcess(false)
                        .build()
        );

//        openManager = new AppOpenManager(this);
    }

    //获取项目上下文
    public static Application getAppContext() {
        return application;
    }
}
