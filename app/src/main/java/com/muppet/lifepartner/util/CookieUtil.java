package com.muppet.lifepartner.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.muppet.lifepartner.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 配置存储工具类
 */
public class CookieUtil {
    public static final String SHAREDPREFERENCES_NAME = "_SharedPreferences"; //SharedPreferences名称

    /**
     * 清空数据
      */
    public static void clear(){
        SharedPreferences sp = App.getAppContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.commit();
    }

    public static void put(String key, Object value){
        SharedPreferences sp = App.getAppContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String){
            edit.putString(key, (String) value);
        }else if (value instanceof Boolean){
            edit.putBoolean(key, (Boolean) value);
        }else if (value instanceof Integer){
            edit.putInt(key, (Integer) value);
        }else if (value instanceof Long){
            edit.putLong(key, (Long) value);
        }else if (value instanceof Float){
            edit.putFloat(key, (Float) value);
        }
        edit.commit();
    }

    /**
     * 读取数据
     * @param key
     * @param defaultValue  默认返回值
     */
    public static Object get(String key, Object defaultValue){
        SharedPreferences sp = App.getAppContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String){
            return sp.getString(key, (String) defaultValue);
        }else if (defaultValue instanceof Boolean){
            return sp.getBoolean(key, (Boolean) defaultValue);
        }else if (defaultValue instanceof Integer){
            return sp.getInt(key, (Integer) defaultValue);
        }else if (defaultValue instanceof Long){
            return sp.getLong(key, (Long) defaultValue);
        }else if (defaultValue instanceof Float){
            return sp.getFloat(key, (Float) defaultValue);
        }
        return  null;
    }

    /**
     * 存储对象
     */
    public static boolean saveObj(Object object){
        boolean isSuccess = false;
        if (object==null){
            return isSuccess;
        }
        FileOutputStream fos = null;
        ObjectOutputStream outputStream =null;
        try {
            //开启文件输出流
            fos = App.getAppContext().openFileOutput(object.getClass().getName(), Context.MODE_PRIVATE);
            //开启对象输出流
            outputStream = new ObjectOutputStream(fos);
            //写入
            outputStream.writeObject(object);
            isSuccess = true;
            return isSuccess;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();//关闭文件流
                outputStream.close();//关闭对象流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    /**
     * 获取对象
     */
    public static <T> T getObj(Class<T> clazz){
        Object object = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            //开启文件输入流
            fis = App.getAppContext().openFileInput(clazz.getName());
            //开启对象输入流
             in = new ObjectInputStream(fis);
            //读取
            object = in.readObject();
            return (T) object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (T) object;
    }
}
