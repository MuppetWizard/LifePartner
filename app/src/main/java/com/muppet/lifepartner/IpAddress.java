package com.muppet.lifepartner;

import android.util.Log;

import androidx.annotation.NonNull;

import com.muppet.lifepartner.util.Constant;

public class IpAddress {

    public static final String Juhe_Server_IP = "http://v.juhe.cn";
    public static final String Juhe_Server_IP_2 = "http://apis.juhe.cn";

    public static String getUrl(@NonNull String url,int ipCode) {
            StringBuilder stringBuilder = new StringBuilder();
        if (ipCode == 0) {
            stringBuilder = new StringBuilder(Juhe_Server_IP);
        } else if (ipCode == 1) {
            stringBuilder = new StringBuilder(Juhe_Server_IP_2);
        }else{
            Log.e(Constant.TAG, "getUrl: ipCode error, Please check" );
        }

        if (!url.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(url);
        return stringBuilder.toString();
    }

    //当天日期
    public static final String CALENDER = "/calendar/day";
    //未来假日
    public static final String HOLODAY = "/calendar/month";
    //快递公司
    public static final String EXP_COM = "/exp/com";
    //快递信息
    public static final String EXPRESS = "/exp/index";
    //当天天气
    public static final String WEATHER = "simpleWeather/query";
    //新闻
    public static final String NEWS = "/toutiao/index";
    //城市
    public static final String CITY = "/simpleWeather/cityList";
}
