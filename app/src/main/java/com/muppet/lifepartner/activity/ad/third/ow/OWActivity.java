package com.muppet.lifepartner.activity.ad.third.ow;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ad.third.baidu.BaiduAdActivity;
import com.muppet.lifepartner.util.StatusUtils;

public class OWActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ow);
        initStatusBar();
        
    }

    private void bindItem(@IdRes int id, final Class clz) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OWActivity.this, clz);
                startActivity(intent);
            }
        });
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.top);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }
}