package com.muppet.lifepartner.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite数据库帮助类
 */
public class MySQliteHelper extends SQLiteOpenHelper {
    public static final String TABLE_MYCITY = "mycity";
    public static final String TABLE_EXPRESS = "express";
    /**
     *
     * @param context  上下文
     * @param name    数据库名称
     * @param factory  游标工厂
     * @param version  版本号（只能增加，不能减少）
     */
    public MySQliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 数据库第一次创建会调用此方法，一般在这里创建表
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mycity(id integer primary key autoincrement,name varchar(64) unique)");
        sqLiteDatabase.execSQL("create table express(id integer primary key autoincrement,com varchar(64),comid varchar(32),num varchar(64),status varchar(32))");
    }

    /**
     * 数据库版本更新调用此方法
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       //删除旧表，创建新表
        sqLiteDatabase.execSQL("drop table if exists mycity");
        sqLiteDatabase.execSQL("drop table if exists express");
        onCreate(sqLiteDatabase);
    }
}
