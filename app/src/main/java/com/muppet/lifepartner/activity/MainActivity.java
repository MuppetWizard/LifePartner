package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.muppet.lifepartner.R;

import com.muppet.lifepartner.fragment.ExpressPage;
import com.muppet.lifepartner.fragment.HomePage;
import com.muppet.lifepartner.fragment.MePage;
import com.muppet.lifepartner.fragment.WeatherPage;
import com.muppet.lifepartner.util.StatusUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity implements RadioGroup.OnCheckedChangeListener {

//    @BindView(R.id.home_btn)
//    RadioButton homeBtn;
    @BindView(R.id.tools_btn)
    RadioButton toolsBtn;
    @BindView(R.id.recreation_btn)
    RadioButton recreationBtn;
    @BindView(R.id.me_btn)
    RadioButton meBtn;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private int curPosition = 0; //当前显示的页码
//    private HomePage homePage;
    private WeatherPage weatherPage;
    private ExpressPage expressPage;
    private MePage mePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initStatusBar();
        initFramgment();//初始化
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initStatusBar() {
        StatusUtils.setSystemStatus(this, true, true);
        LinearLayout llTop = findViewById(R.id.page_container);
        llTop.setPadding(0, StatusUtils.getStatusBarHeight(this), 0, 0);
    }

    private void initFramgment() { //初始化Fragment
        weatherPage = findFragment(WeatherPage.class);
        if (weatherPage != null) {
//            weatherPage = findFragment(WeatherPage.class);
            expressPage = findFragment(ExpressPage.class);
            mePage = findFragment(MePage.class);
        } else { //没有找到控件则新建并加载
//            homePage = new HomePage();
            weatherPage = new WeatherPage();
            expressPage = new ExpressPage();
            mePage = new MePage();
        }
        //加载Fragment  绑定framelayout
        loadMultipleRootFragment(R.id.frame_main, curPosition,
//                homePage,
                weatherPage,
                expressPage,
                mePage);
    }
    //获取当前Fragment
    private ISupportFragment getPreferences() {
        switch (curPosition) {
            case 0:
//                return homePage;
                return weatherPage;
            case 1:
                return expressPage;
            case 2:
                return mePage;
            /*case 3:
                return mePage;*/
        }
        return null;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            /*case R.id.home_btn:
                showHideFragment(homePage, getPreferences());
                curPosition = 0;
                break;*/
            case R.id.tools_btn:
                showHideFragment(weatherPage, getPreferences());
                curPosition = 0;
                break;
            case R.id.recreation_btn:
                showHideFragment(expressPage, getPreferences());
                curPosition = 1;
                break;
            case R.id.me_btn:
                showHideFragment(mePage, getPreferences());
                curPosition = 2;
                break;
        }
    }

}
