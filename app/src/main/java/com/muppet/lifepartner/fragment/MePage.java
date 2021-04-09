package com.muppet.lifepartner.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActAboutUs;
import com.muppet.lifepartner.activity.ActMyFeadback;
import com.muppet.lifepartner.activity.ActWebview;
import com.muppet.lifepartner.util.Constant;
import com.youyi.yesdk.ad.RewardVideoAd;
import com.youyi.yesdk.business.UERewardManager;
import com.youyi.yesdk.business.YOUEAdConstants;
import com.youyi.yesdk.listener.RewardListener;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

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
    @BindView(R.id.protocol)
    LinearLayout protocol;
    @BindView(R.id.ll_reward_csj)
    LinearLayout llRewardCsj;
    @BindView(R.id.ll_reward_gdt)
    LinearLayout llRewardGdt;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.me_page, null);
        ButterKnife.bind(this,view);

        return view;
    }

    private void loadRewardVideo(String id) {
        RewardVideoAd ad = new RewardVideoAd();
        ad.setRewardConfig(getActivity(),new UERewardManager()
//                .setUserID("youe_TEST")
//                .setCustomData("youe_data")
                .setExpressViewAcceptedSize(500f,500f)
                .setScenes(YOUEAdConstants.RitScenes.CUSTOMIZE_SCENES,"scenes_test")
                .setOrientation(YOUEAdConstants.HORIZONTAL)
                .build()
        ).loadRewardVideo(id, new RewardListener() {
            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.d(Constant.TAG,"onError : code: "+ integer+" msg : "+ s);
            }

            @Override
            public void onVideoCached() {
                Log.d(Constant.TAG,"onVideoCached");
                ad.show();
            }

            @Override
            public void onADLoaded() {
                Log.d(Constant.TAG,"onADLoaded");
            }

            @Override
            public void onADShow() {
                Log.d(Constant.TAG,"onADShow");
            }

            @Override
            public void onReward(@Nullable Boolean aBoolean, @Nullable Integer integer, @Nullable String s, @Nullable Integer integer1, @Nullable String s1, @Nullable Map<String, Object> map) {
                Log.d(Constant.TAG,"onReward " + aBoolean +" id: "+integer+" msg: "+ s+ " errorC: "+ integer+ " errorM: "+ s1);
            }

            @Override
            public void onADComplete() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onVideoBarClick() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onSKipVideo() {
                Log.d(Constant.TAG,"onADComplete");
            }

            @Override
            public void onClosed() {
                Log.d(Constant.TAG,"onADComplete");
            }
        });

    }

    @OnClick({R.id.my_about_as, R.id.my_feedback, R.id.protocol,R.id.ll_reward_csj,R.id.ll_reward_gdt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_about_as:
                startActivity(new Intent(getContext(), ActAboutUs.class));
                break;
            case R.id.my_feedback:
                startActivity(new Intent(getContext(), ActMyFeadback.class));
                break;
            case R.id.protocol:
                Intent intent = new Intent(getContext(), ActWebview.class);
                intent.putExtra("URL","https://www.hlhjapp.com/website/agreement/137");
                startActivity(intent);
                break;
            case R.id.ll_reward_csj:
                loadRewardVideo("0000000034");
                break;
            case R.id.ll_reward_gdt:
                loadRewardVideo("0000000033");
                break;
        }
    }
}
