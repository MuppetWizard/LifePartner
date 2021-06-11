package com.muppet.lifepartner;

import android.app.Application;
import android.util.Log;

import com.baidu.mobads.AdView;
import com.baidu.mobads.MobadsPermissionSettings;
import com.inmobi.sdk.InMobiSdk;
import com.mbridge.msdk.MBridgeSDK;
import com.mbridge.msdk.out.MBridgeSDKFactory;
import com.mbridge.msdk.out.SDKInitStatusListener;
import com.muppet.lifepartner.util.Constant;
import com.youyi.yesdk.YOUEAdSdk;
import com.youyi.yesdk.business.YOUEAdManager;

import org.xutils.x;

import java.util.Map;

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
//        initMBSkd("144819","5462e2032d96955e966454fecb8e1580");
        //测试
        initMBSkd("118690","7c22942b749fe6a6e361b675e96b3ee9");
        //baidu
        AdView.setAppSid(application,"c9f473aa");
        //debug
//        AdView.setAppSid(application,"e866cfb0");
        MobadsPermissionSettings.setPermissionReadDeviceID(true);
        MobadsPermissionSettings.setPermissionAppList(true);
        //Inmob
//        InMobiSdk.init(this, "550d78c18791d7e161e788ed734eb064");
//        InMobiSdk.init(this,"35cd4640484c490d8d7b59484fa52952");
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);

    }

    private void initMBSkd(String appId,String appKey) {

        MBridgeSDK mbSdk = MBridgeSDKFactory.getMBridgeSDK();
        Map<String, String> map = mbSdk.getMBConfigurationMap(appId,appKey);
        mbSdk.init(map, application, new SDKInitStatusListener() {
            @Override
            public void onInitSuccess() {
                Log.d(Constant.TAG, "onInitSuccess");
            }

            @Override
            public void onInitFail() {
                Log.d(Constant.TAG, "onInitFail");
            }
        });
    }

    private void initUEAdSdk() {
        
        YOUEAdSdk.INSTANCE.initSDK(application,
                new YOUEAdManager()
                        .appId("000012")
                        .appName("游易")
                        .deBug(false)
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
