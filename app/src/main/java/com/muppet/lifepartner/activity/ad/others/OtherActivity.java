package com.muppet.lifepartner.activity.ad.others;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.muppet.lifepartner.App;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.StatusUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OtherActivity extends AppCompatActivity {

    private Context context = OtherActivity.this;

    private String googleKey = "com.google.android.gms.ads.APPLICATION_ID";
    private TextView tvOutput;
    private ImageView ivImg;

    public static final int GET_DATA_SUCCESS = 1;
    public static final int NETWORK_ERROR = 2;
    public static final int SERVER_ERROR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        initStatusBar();
        tvOutput = findViewById(R.id.tv_output);
        ivImg = findViewById(R.id.iv_bg);
        bindItem(R.id.btn_write);
        bindItem(R.id.btn_read);
        bindItem(R.id.btn_other);
        String deviceID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
//        tvOutput.setText(TestUtils.INSTANCE.testFor("13465431|65465"));
        tvOutput.setText(deviceID);
        setOnclick();
    }

    private void bindItem(@IdRes int id) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()) {
                    case R.id.btn_write:
                        writeMetaData("123456789");
//                        loadImg("https://img.hlhjapp.com/ad/ad_img.jpg");
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

    private void setOnclick() {
        tvOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) OtherActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                String deviceID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
                manager.setPrimaryClip(ClipData.newPlainText(null,deviceID));

            }
        });
    }

    //子线程不能操作UI，通过Handler设置图片
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    ivImg.setImageBitmap(bitmap);
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(OtherActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(OtherActivity.this,"服务器发生错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void loadImg(String path) {
        new Thread() {
            @Override
            public void run() {
                try {
                    //把传过来的路径转成URL
                    URL url = new URL(path);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 设置输入可用
                    connection.setDoInput(true);
                    // 设置输出可用
//                    connection.setDoOutput(true);
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为10秒
                    connection.setConnectTimeout(10000);
                    // 设置缓存不可用
                    connection.setUseCaches(true);
                    // 开始连接
                    connection.connect();
                    //获取返回码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //利用Message把图片发给Handler
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        msg.what = GET_DATA_SUCCESS;
                        handler.sendMessage(msg);
                        inputStream.close();
                    }else {
                        //服务启发生错误
                        handler.sendEmptyMessage(SERVER_ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //网络连接错误
                    handler.sendEmptyMessage(NETWORK_ERROR);
                }
            }
        }.start();
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

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this,true,true);
        LinearLayout llTop = findViewById(R.id.page_container);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this),0,0);
    }
}