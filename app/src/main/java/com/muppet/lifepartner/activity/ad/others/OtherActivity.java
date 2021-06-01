package com.muppet.lifepartner.activity.ad.others;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.muppet.lifepartner.App;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.StatusUtils;

public class OtherActivity extends AppCompatActivity {

    private String googleKey = "com.google.android.gms.ads.APPLICATION_ID";
    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        initStatusBar();
        tvOutput = findViewById(R.id.tv_output);
        bindItem(R.id.btn_write);
        bindItem(R.id.btn_read);
        bindItem(R.id.btn_other);
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.page_container);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }

    private void bindItem(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.btn_write:
                        writeMetaData("123456789");
                        break;
                    case R.id.btn_read:
                        tvOutput.setText(readMetaData());
                        break;
                    case R.id.btn_other:
                        intent = new Intent(OtherActivity.this,ViewTestActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void writeMetaData(String value) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = App.application.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            appInfo.metaData.putString(googleKey,value);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String readMetaData() {
        ApplicationInfo appInfo = null;
        String value;
        try {
            appInfo = App.application.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(googleKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "wrong";
        }
        return value;
    }
}