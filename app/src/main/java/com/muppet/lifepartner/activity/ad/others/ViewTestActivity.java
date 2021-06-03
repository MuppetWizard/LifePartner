package com.muppet.lifepartner.activity.ad.others;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.StatusUtils;
import com.muppet.lifepartner.util.UIUtils;

public class ViewTestActivity extends AppCompatActivity {

    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);
        initStatusBar();
        bindItem(R.id.btn_test_splash);
        container = findViewById(R.id.view_container);
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
                switch (v.getId()) {
                    case R.id.btn_test_splash:
                        SplashController splashController = new SplashController(ViewTestActivity.this);
                        splashController.startTimer(6);
                        container.addView(splashController);
                        int  minHeight = UIUtils.getScreenHeight(ViewTestActivity.this) ;
                        int minWidth = UIUtils.getScreenWidth(ViewTestActivity.this);
                        container.post(new Runnable() {
                            @Override
                            public void run() {
                                int actualWidth = splashController.getWidth();
                                int actualHeight = splashController.getHeight();
                                if (minWidth != actualWidth) {
                                    Log.d(Constant.TAG,"width error");
                                } else if (minHeight * 0.75 > actualHeight) {
                                    Log.d(Constant.TAG,"height error");
                                }
                                long bb = (actualHeight/minHeight) *100;
                                Log.d(Constant.TAG,"actualWidth: "+ actualWidth+ " actualHeight: "+ actualHeight+
                                        "\n minWidth:"+minWidth+ " minHeight: "+minHeight+ " %: "+bb);
                            }
                        });

                        break;
                }
            }
        });
    }
}