package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActWebview extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_webview);
        ButterKnife.bind(this);
        initWebView();
    }

    private void initWebView() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("URL")) {
            String url = intent.getStringExtra("URL");
            webview.loadUrl(url);
            Log.d(Constant.TAG, "initWebView: "+url);
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
