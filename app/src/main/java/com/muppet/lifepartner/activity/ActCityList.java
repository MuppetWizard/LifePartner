package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.muppet.lifepartner.IpAddress;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.adapter.CityAdapter;
import com.muppet.lifepartner.mode.City;
import com.muppet.lifepartner.mode.LetterCityMode;
import com.muppet.lifepartner.mode.MessageEvent;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.PinyinComparator;
import com.muppet.lifepartner.util.PinyinUtils;
import com.muppet.lifepartner.view.CitySearchEdittext;
import com.muppet.lifepartner.view.TitleItemDecoration;
import com.muppet.lifepartner.view.WaveSideBar;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActCityList extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.sideBar)
    WaveSideBar sideBar;
    @BindView(R.id.city_search)
    CitySearchEdittext citySearch;
    @BindView(R.id.progress02)
    ProgressBar progress02;

    private static final String API = "http://apis.juhe.cn/simpleWeather/cityList";
    private List<String> arrayCity;
    private List<LetterCityMode> mDataList;
    private LinearLayoutManager manager;
    private CityAdapter cityAdapter;
    private TitleItemDecoration mDecoration;
    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator mComparator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_city_list);
        ButterKnife.bind(this);
        getCity();
    }

    private void getCity() {
        arrayCity = new ArrayList<>();
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.CITY,1));
        params.addBodyParameter("key", Constant.WEATHER_KEY);
        x.http().get(params, new Callback.CommonCallback<City>() {
            @Override
            public void onSuccess(City result) {
                if (result != null) {
                    List<City.ResultBean> resultBeans = result.getResult();
                    if (resultBeans != null) {
                        progress02.setVisibility(View.GONE);
                        for (int i = 0; i < resultBeans.size(); i++) {
                            arrayCity.add(resultBeans.get(i).getDistrict());
                        }
                        initView();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initView() {

        mComparator = new PinyinComparator();
        //设置右侧SideBar触摸监听
        sideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = cityAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        //填充数据
        mDataList = filledData(arrayCity);
        // 根据a-z进行排序源数据
        Collections.sort(mDataList, mComparator);
        //RecyclerView设置manager
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        cityAdapter = new CityAdapter(this, mDataList);
        recyclerView.setAdapter(cityAdapter);
        mDecoration = new TitleItemDecoration(this, mDataList);
        cityAdapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object name) {
                EventBus.getDefault().post(new MessageEvent(name.toString()));
                finish();
            }
        });
        //如果add两个，那么按照先后顺序，依次渲染。
        recyclerView.addItemDecoration(mDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(ActCityList.this, DividerItemDecoration.VERTICAL));

        //根据输入框输入值的改变来过滤搜索
        citySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 为RecyclerView填充数据
     *
     * @param data
     * @return
     */
    private List<LetterCityMode> filledData(List<String> data) {
        List<LetterCityMode> cityModes = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            LetterCityMode letterCityMode = new LetterCityMode();
            letterCityMode.setName(data.get(i));
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(data.get(i));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                letterCityMode.setLetters(sortString.toUpperCase());
            } else {
                letterCityMode.setLetters("#");
            }
            cityModes.add(letterCityMode);
        }
        return cityModes;
    }

    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<LetterCityMode> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = filledData(arrayCity);
        } else {
            filterDateList.clear();
            for (LetterCityMode sortModel : mDataList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr)
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr)
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                ) {
                    filterDateList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, mComparator);
        mDataList.clear();
        mDataList.addAll(filterDateList);
        cityAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
