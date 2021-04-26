package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kwad.sdk.api.KsSplashScreenAd;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ad.SplashVPlusAd;
import com.muppet.lifepartner.fragment.ExpressPage;
import com.muppet.lifepartner.fragment.HomePage;
import com.muppet.lifepartner.fragment.MePage;
import com.muppet.lifepartner.fragment.WeatherPage;

import butterknife.BindView;
import butterknife.ButterKnife;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.home_btn)
    RadioButton homeBtn;
    @BindView(R.id.tools_btn)
    RadioButton toolsBtn;
    @BindView(R.id.recreation_btn)
    RadioButton recreationBtn;
    @BindView(R.id.me_btn)
    RadioButton meBtn;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private int curPosition = 0; //当前显示的页码
    private HomePage homePage;
    private WeatherPage weatherPage;
    private ExpressPage expressPage;
    private MePage mePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFramgment();//初始化
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (SplashVPlusAd.ksSplashScreenAd != null) {
            Rect rect = new Rect();
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            rect.right = displayMetrics.widthPixels;
            rect.left =  rect.right - displayMetrics.widthPixels / 4;
            rect.bottom = (int) (displayMetrics.heightPixels * 0.83f);
            rect.top = rect.bottom - (displayMetrics.widthPixels / 4) * 16 / 9;
            SplashVPlusAd.ksSplashScreenAd.showSplashMiniWindowIfNeeded(this,
                    new KsSplashScreenAd.SplashScreenAdInteractionListener() {
                        @Override
                        public void onAdClicked() {

                        }

                        @Override
                        public void onAdShowError(int i, String s) {

                        }

                        @Override
                        public void onAdShowEnd() {

                        }

                        @Override
                        public void onAdShowStart() {

                        }

                        @Override
                        public void onSkippedAd() {

                        }
                    },rect);
            SplashVPlusAd.ksSplashScreenAd = null;
        }

    }

    private void initFramgment() { //初始化Fragment
        homePage = findFragment(HomePage.class);
        if (homePage != null) {
            weatherPage = findFragment(WeatherPage.class);
            expressPage = findFragment(ExpressPage.class);
            mePage = findFragment(MePage.class);
        } else { //没有找到控件则新建并加载
            homePage = new HomePage();
            weatherPage = new WeatherPage();
            expressPage = new ExpressPage();
            mePage = new MePage();
        }
        //加载Fragment  绑定framelayout
        loadMultipleRootFragment(R.id.frame_main, curPosition,
                homePage,
                weatherPage,
                expressPage,
                mePage);
    }
    //获取当前Fragment
    private ISupportFragment getPreferences() {
        switch (curPosition) {
            case 0:
                return homePage;
            case 1:
                return weatherPage;
            case 2:
                return expressPage;
            case 3:
                return mePage;
        }
        return null;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.home_btn:
                showHideFragment(homePage, getPreferences());
                curPosition = 0;
                break;
            case R.id.tools_btn:
                showHideFragment(weatherPage, getPreferences());
                curPosition = 1;
                break;
            case R.id.recreation_btn:
                showHideFragment(expressPage, getPreferences());
                curPosition = 2;
                break;
            case R.id.me_btn:
                showHideFragment(mePage, getPreferences());
                curPosition = 3;
                break;
        }
    }

}
