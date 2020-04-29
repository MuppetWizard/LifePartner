package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.muppet.lifepartner.IpAddress;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.adapter.HolidayAdapter;
import com.muppet.lifepartner.mode.Calendar;
import com.muppet.lifepartner.mode.Holiday;
import com.muppet.lifepartner.util.App;
import com.muppet.lifepartner.util.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActCalendar extends AppCompatActivity {

    @BindView(R.id.annals)
    TextView annals;
    @BindView(R.id.nongli)
    TextView nongli;
    @BindView(R.id.week)
    TextView week;
    @BindView(R.id.realdate)
    TextView realdate;
    @BindView(R.id.today)
    TextView today;
    @BindView(R.id.ji)
    TextView ji;
    @BindView(R.id.yi)
    TextView yi;
    @BindView(R.id.holiday)
    ListView holiday;
    @BindView(R.id.back)
    ImageView back;


    private Date date;
    private HolidayAdapter holidayAdapter;
    private List<Holiday.ResultBean.DataBean.HolidayArrayBean> holidayArrayBeans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_calendar);
        ButterKnife.bind(this);
        holidayAdapter = new HolidayAdapter(this);
        holiday.setAdapter(holidayAdapter);
        getCalendar();
        getHoliday();
    }

    /**
     * 请求获取当天万年历数据
     */
    private void getCalendar() {
        //获取系统日期
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        String year = Integer.toString(calendar.get(java.util.Calendar.YEAR));
        String month = Integer.toString(calendar.get(java.util.Calendar.MONTH) + 1);
        String day = Integer.toString(calendar.get(java.util.Calendar.DAY_OF_MONTH));
        final String time = year + "-" + month + "-" + day;

        //请求
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.CALENDER,0));
        params.addBodyParameter("key", Constant.CALENDER_KEY);
        params.addBodyParameter("date", time);
        x.http().get(params, new Callback.CommonCallback<Calendar>() {

            @Override
            public void onSuccess(Calendar result) {
                Calendar calendar = result;
                if (calendar.getResult() != null) {
                    Calendar.ResultBean.DataBean data = calendar.getResult().getData();
                    setdata(data);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(App.getAppContext(), "获取数据失败" + time, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取假日信息
     */
    private void getHoliday() {
        holidayArrayBeans = new ArrayList<>();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        String year = Integer.toString(calendar.get(java.util.Calendar.YEAR));
        String month = Integer.toString(calendar.get(java.util.Calendar.MONTH) + 1);
        String time = year + "-" + month;
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.HOLODAY,0));
        params.addBodyParameter("key", Constant.CALENDER_KEY);
        params.addBodyParameter("year-month", time);
        x.http().get(params, new Callback.CommonCallback<Holiday>() {
            @Override
            public void onSuccess(Holiday result) {
                Holiday holiday = result;
                if (holiday.getResult() != null) {
                    holidayArrayBeans = holiday.getResult().getData().getHoliday_array();
                    holidayAdapter.setholidayData(holidayArrayBeans);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void setdata(Calendar.ResultBean.DataBean data) {
        date = new Date();
        String animalsYear = data.getAnimalsYear();
        String jinian = data.getLunarYear();
        annals.setText(jinian + " 生肖:" + animalsYear);
        String lunar = data.getLunar();
        nongli.setText("农历" + lunar);
        String wkday = data.getWeekday();
        week.setText(wkday);
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String Today = sdf.format(date);
        today.setText(Today);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");
        String riqi = sdf2.format(date);
        realdate.setText(riqi);
        String Ji = data.getAvoid();
        ji.setText(Ji);
        String Yi = data.getSuit();
        yi.setText(Yi);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

}
