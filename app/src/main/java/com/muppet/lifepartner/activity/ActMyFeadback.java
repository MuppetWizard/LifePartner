package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.muppet.lifepartner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActMyFeadback extends AppCompatActivity {

    @BindView(R.id.go_back)
    ImageView goBack;
    @BindView(R.id.save_opinion)
    TextView saveOpinion;
    @BindView(R.id.et_content)
    EditText etContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_feadback);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.go_back, R.id.save_opinion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_back:
                finish();
                break;
            case R.id.save_opinion:
                finish();
                break;
        }
    }
}
