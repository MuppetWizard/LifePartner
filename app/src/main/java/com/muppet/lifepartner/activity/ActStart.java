package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.CookieUtil;
import com.muppet.lifepartner.view.UserA;

public class ActStart extends AppCompatActivity implements Runnable{

    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start);
        mCount = (int) CookieUtil.get("isFirst",0);
        if (mCount == 0) {
            UserA dialog = new UserA(this);
            dialog.show();
        }else {
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1500);

            Intent intent = new Intent();
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

