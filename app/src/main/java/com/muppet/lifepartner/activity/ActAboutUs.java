package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.muppet.lifepartner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActAboutUs extends AppCompatActivity {

    @BindView(R.id.goback)
    ImageView goback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_about_us);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.goback)
    public void onViewClicked() {
        finish();
    }
}
