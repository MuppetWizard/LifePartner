 package com.muppet.lifepartner.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActGuide;
import com.muppet.lifepartner.activity.ActWebview;
import com.muppet.lifepartner.util.CookieUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * des:
 *
 * @author muppet
 * @date 2021/4/2
 */
public class UserA extends Dialog {
    private Activity mContent;

    public UserA(@NonNull Activity context) {
        super(context, R.style.app_dialog);
        this.mContent = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_user_a);
        ButterKnife.bind(this);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        DisplayMetrics dm = mContent.getResources().getDisplayMetrics();
        params.width= WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.yhxy, R.id.btn_next,R.id.tv_liulan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yhxy:
                Intent intent = new Intent(getContext(), ActWebview.class);
                intent.putExtra("URL","https://www.hlhjapp.com/website/agreement/137");
                mContent.startActivity(intent);
                break;
            case R.id.btn_next:
                CookieUtil.put("isFirst",1);
                mContent.startActivity(new Intent(getContext(), ActGuide.class));
                dismiss();
                mContent.finish();
                break;
            case R.id.tv_liulan:
                mContent.finish();
                break;
            default:
                break;
        }

    }

}
