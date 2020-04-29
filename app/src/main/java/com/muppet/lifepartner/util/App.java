package com.muppet.lifepartner.util;

import android.app.Application;

import org.xutils.x;

public class App extends Application {
    public static Application application;
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
    }

    //获取项目上下文
    public static Application getAppContext() {
        return application;
    }
}
