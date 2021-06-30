package com.muppet.lifepartner;

import android.app.Application;
import android.util.Log;

import com.baidu.mobads.sdk.api.BDAdConfig;
import com.baidu.mobads.sdk.api.MobadsPermissionSettings;
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

        //baidu Plus
        BDAdConfig adConfig = new BDAdConfig.Builder()
                .setAppsid("c9f473aa")
                .setAppName("游易")
                .build(this);
        adConfig.init();
        // 设置SDK可以使用的权限，包含：设备信息、定位、存储、APP LIST
        // 注意：建议授权SDK读取设备信息，SDK会在应用获得系统权限后自行获取IMEI等设备信息
        // 授权SDK获取设备信息会有助于提升ECPM
        MobadsPermissionSettings.setPermissionReadDeviceID(true);
        MobadsPermissionSettings.setPermissionLocation(true);
        MobadsPermissionSettings.setPermissionStorage(true);
        MobadsPermissionSettings.setPermissionAppList(true);

        //
//        initMBSkd("144819","5462e2032d96955e966454fecb8e1580");
        //测试
//        initMBSkd("118690","7c22942b749fe6a6e361b675e96b3ee9");

        //baidu
//        AdView.setAppSid(application,"c9f473aa");
        //debug
//        AdView.setAppSid(application,"e866cfb0");
//        MobadsPermissionSettings.setPermissionReadDeviceID(true);
//        MobadsPermissionSettings.setPermissionAppList(true);

        //Inmob
//        InMobiSdk.init(this, "550d78c18791d7e161e788ed734eb064");
//        InMobiSdk.init(this,"35cd4640484c490d8d7b59484fa52952");
//        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);

        //oneway
//        OnewaySdk.configure(this,"cde1f85bdaf2435a");
//        OnewaySdk.setDebugMode(true);
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
        //release-000012
        //beta-000002
        YOUEAdSdk.INSTANCE.initSDK(application,
                new YOUEAdManager()
                        .appId("000012")
                        .appName("游易")
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
