package com.muppet.lifepartner;

import android.app.Application;


import com.baidu.mobads.AdView;
import com.youyi.yesdk.YOUEAdSdk;
import com.youyi.yesdk.business.YOUEAdManager;
import com.youyi.yesdk.comm.holder.GDTAdManagerHolder;

import org.xutils.x;

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
        AdView.setAppSid(application,"c9f473aa");
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
