package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.CookieUtil;

public class ActStart extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start);
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1500);
            int count = (int) CookieUtil.get("isFirst",0);
            Intent intent = new Intent();
            if (count == 0) {
                intent.setClass(this, ActGuide.class);
            } else {
                intent.setClass(this,MainActivity.class);
            }
            startActivity(intent);
            CookieUtil.put("isFirst",1);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

