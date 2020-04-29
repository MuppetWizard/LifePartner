package com.muppet.lifepartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.muppet.lifepartner.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActGuide extends AppCompatActivity {

    @BindView(R.id.guide_page)
    ConvenientBanner guidePage;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.skip)
    Button skip;
    private ArrayList<Integer> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_guide);
        ButterKnife.bind(this);
        initView();
        initGuidePage();
    }

    private void initView() {
        arrayList = new ArrayList<>();
        arrayList.add(R.mipmap.guide02);
        arrayList.add(R.mipmap.guide01);
        arrayList.add(R.mipmap.start);
    }

    private void initGuidePage() {
        guidePage.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new LocalImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.guide_page;
            }
        }, arrayList).setPageIndicator(new int[]{R.mipmap.dian01, R.mipmap.dian02})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPointViewVisible(true)
                .setCanLoop(false)
                .setOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    }

                    @Override
                    public void onPageSelected(int index) {
                        if (index == 2) {
                            btnStart.setVisibility(View.VISIBLE);
                            guidePage.setPointViewVisible(false);
                        } else {
                            btnStart.setVisibility(View.GONE);
                            guidePage.setPointViewVisible(true);
                        }
                    }
                });
    }

    @OnClick({R.id.btn_start, R.id.skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.skip:
                finish();
                break;
        }
    }

    public static class LocalImageHolderView extends Holder<Integer> {
        private ImageView mImageView;

        public LocalImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            mImageView = itemView.findViewById(R.id.guide_img);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        @Override
        public void updateUI(Integer data) {
            mImageView.setImageResource(data);
        }
    }
}
