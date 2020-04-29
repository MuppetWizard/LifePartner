package com.muppet.lifepartner.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActAboutUs;
import com.muppet.lifepartner.activity.ActMyFeadback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MePage extends SupportFragment {


    @BindView(R.id.my_about_as)
    LinearLayout myAboutAs;
    @BindView(R.id.my_feedback)
    LinearLayout myFeedback;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.me_page, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({R.id.my_about_as, R.id.my_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_about_as:
                startActivity(new Intent(getContext(), ActAboutUs.class));
                break;
            case R.id.my_feedback:
                startActivity(new Intent(getContext(), ActMyFeadback.class));
                break;
        }
    }
}
