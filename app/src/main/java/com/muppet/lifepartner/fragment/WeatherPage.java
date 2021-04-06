package com.muppet.lifepartner.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.muppet.lifepartner.IpAddress;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActaddCity;
import com.muppet.lifepartner.mode.MessageEvent;
import com.muppet.lifepartner.mode.Weather;
import com.muppet.lifepartner.util.App;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.MySQliteHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherPage extends SupportFragment {

    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.manage)
    ImageView manage;
    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.date_info)
    TextView dateInfo;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.weather_status)
    ImageView weatherStatus;
    @BindView(R.id.wind_power)
    TextView windPower;
    @BindView(R.id.wind_direct)
    TextView windDirect;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.wind_aqi)
    TextView windAqi;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.wind_humidity)
    TextView windHumidity;
    @BindView(R.id.linear4)
    LinearLayout linear4;
    @BindView(R.id.ziwaixian)
    TextView ziwaixian;
    @BindView(R.id.linear5)
    LinearLayout linear5;
    @BindView(R.id.future_img01)
    ImageView futureImg01;
    @BindView(R.id.future_img02)
    ImageView futureImg02;
    @BindView(R.id.future_date01)
    TextView futureDate01;
    @BindView(R.id.future_img03)
    ImageView futureImg03;
    @BindView(R.id.future_date02)
    TextView futureDate02;
    @BindView(R.id.future_img04)
    ImageView futureImg04;
    @BindView(R.id.future_date03)
    TextView futureDate03;
    @BindView(R.id.future_img05)
    ImageView futureImg05;
    @BindView(R.id.future_date04)
    TextView futureDate04;
    @BindView(R.id.future_chart)
    LineChartView futureChart;
    Unbinder unbinder;
    @BindView(R.id.progress01)
    ProgressBar progress01;

    private Date date;
    private List<Integer> FutureTemp;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<Weather.ResultBean.FutureBean> futureData;

    private Weather.ResultBean.RealtimeBean weather;
    private List<String> futureC;
    private String strStart = "/";
    private String strEnd = "℃";
    private SQLiteDatabase db;
    private String mcity = "成都";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.weather_page, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        getWeather();//获取天气数据
        MySQliteHelper helper = new MySQliteHelper(App.getAppContext(), "Lifepartner.db", null, 1);
        //创建或打开一个可读可写的数据库
        db = helper.getReadableDatabase();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    /**
     * 设置图表数据
     * @param futureC
     */
    public void setChart(List<String> futureC) {
        FutureTemp = new ArrayList<>();
        for (int i = 0; i < futureC.size(); i++) {
            String oC = futureC.get(i);
            //截取字符串
            FutureTemp.add(Integer.parseInt(Objects.requireNonNull(subString(oC, strStart, strEnd))));
        }
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {
        //找出指定的2个字符在 该字符串里面的 位置
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);
        //index 为负数 即表示该字符串中 没有该字符
        if (strStartIndex < 0) {
            Toast.makeText(App.getAppContext(), "字符串-->" + str + "<--中不存在" + strStartIndex + "无法截取", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (strEndIndex < 0) {
            Toast.makeText(App.getAppContext(), "字符串-->" + str + "<--中不存在" + strEndIndex + "无法截取", Toast.LENGTH_SHORT).show();
            return null;
        }
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }
    /**
     * 获取天气数据
     */
    private void getWeather() {
        futureC = new ArrayList<>();
        futureData = new ArrayList<>();
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.WEATHER,1));
        params.addBodyParameter("key", Constant.WEATHER_KEY);
        params.addBodyParameter("city", mcity);
        x.http().get(params, new Callback.CommonCallback<Weather>() {
            @Override
            public void onSuccess(Weather result) {
                if (result != null) {
                    Weather.ResultBean weatherData = result.getResult();
                    if (weatherData != null) {
                        progress01.setVisibility(View.GONE);
                        weather = weatherData.getRealtime();
                        futureData = weatherData.getFuture();
                        setWeather(weather, weatherData, futureData);
                        for (int i = 0; i < weatherData.getFuture().size(); i++) {
                            futureC.add(weatherData.getFuture().get(i).getTemperature());
                        }
                        setChart(futureC);
                        saveCity(weatherData.getCity());
                    }
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

    /**
     * 保存常用城市
     *
     * @param city
     */
    private void saveCity(String city) {
        boolean hasdata = false;
        Cursor cursor = db.query(MySQliteHelper.TABLE_MYCITY, new String[]{"name"}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            if (name.equals(city)) {
                hasdata = true;
                break;
            } else {
                Log.e("123", "saveCity: 已存在");
            }
        }
        if (!hasdata) {
            //插入`
            ContentValues values = new ContentValues();
            values.put("name", city);
            long rowId = db.insert(MySQliteHelper.TABLE_MYCITY, null, values);
            //返回新添标记的行号，该行号是一个内部值，与主键无关，当发送错误为-1
            if (rowId == -1) {
                Log.e("111", "saveCity:保存失败 ");
            } else {
                Log.e("111", "saveCity: 保存成功");
            }
        }
    }

    /**
     * 设置天气数据
     * @param weather
     * @param weatherData
     * @param futureData
     */
    private void setWeather(Weather.ResultBean.RealtimeBean weather, Weather.ResultBean weatherData, List<Weather.ResultBean.FutureBean> futureData) {
        String Temperature = weather.getTemperature();
        String Humidity = weather.getHumidity();
        String Info = weather.getInfo();
        String Wid = weather.getWid();
        String Direct = weather.getDirect();
        String Power = weather.getPower();
        String Aqi = weather.getAqi();
        String City = weatherData.getCity();
        //城市
        city.setText(City);
        //温度
        temperature.setText(Temperature);
        //湿度
        windHumidity.setText(Humidity + "%");
        //获取系统时间
        date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String taday = sdf.format(date);
        //日期+天气情况
        dateInfo.setText(taday + "/" + Info);
        //风力
        windPower.setText(Power);
        //风向
        windDirect.setText(Direct);
        //空气质量
        windAqi.setText(Aqi);
        //天气状况图片
        switch (Wid) {
            case "00":
                weatherStatus.setImageResource(R.mipmap.sunshine);
                break;
            case "01":
                weatherStatus.setImageResource(R.mipmap.cloudy);
                break;
            case "02":
                weatherStatus.setImageResource(R.mipmap.cloudy02);
                break;
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
            case "10":
            case "11":
            case "12":
            case "13":
            case "14":
            case "15":
            case "16":
            case "17":
            case "18":
            case "19":
                weatherStatus.setImageResource(R.mipmap.rain);
                break;
        }
        //未来5天气情况图片
        String day1 = futureData.get(0).getWid().getDay();
        switch (day1) {
            case "00":
                futureImg01.setImageResource(R.mipmap.sunshine);
                break;
            case "01":
                futureImg01.setImageResource(R.mipmap.cloudy);
                break;
            case "02":
                futureImg01.setImageResource(R.mipmap.cloudy02);
                break;
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
                futureImg01.setImageResource(R.mipmap.rain);
                break;
        }
        String day2 = futureData.get(1).getWid().getDay();
        switch (day2) {
            case "00":
                futureImg02.setImageResource(R.mipmap.sunshine);
                break;
            case "01":
                futureImg02.setImageResource(R.mipmap.cloudy);
                break;
            case "02":
                futureImg02.setImageResource(R.mipmap.cloudy02);
                break;
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
                futureImg02.setImageResource(R.mipmap.rain);
                break;
        }
        String day3 = futureData.get(2).getWid().getDay();
        switch (day3) {
            case "00":
                futureImg03.setImageResource(R.mipmap.sunshine);
                break;
            case "01":
                futureImg03.setImageResource(R.mipmap.cloudy);
                break;
            case "02":
                futureImg03.setImageResource(R.mipmap.cloudy02);
                break;
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
                futureImg03.setImageResource(R.mipmap.rain);
                break;
        }
        String day4 = futureData.get(3).getWid().getDay();
        switch (day4) {
            case "00":
                futureImg04.setImageResource(R.mipmap.sunshine);
                break;
            case "01":
                futureImg04.setImageResource(R.mipmap.cloudy);
                break;
            case "02":
                futureImg04.setImageResource(R.mipmap.cloudy02);
                break;
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
                futureImg04.setImageResource(R.mipmap.rain);
                break;
        }
        String day5 = futureData.get(4).getWid().getDay();
        switch (day5) {
            case "00":
                futureImg05.setImageResource(R.mipmap.sunshine);
                break;
            case "01":
                futureImg05.setImageResource(R.mipmap.cloudy);
                break;
            case "02":
                futureImg05.setImageResource(R.mipmap.cloudy02);
                break;
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
                futureImg05.setImageResource(R.mipmap.rain);
                break;
        }
        //未来时间
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        futureDate02.setText(getFetureDate(2));
        futureDate03.setText(getFetureDate(3));
        futureDate04.setText(getFetureDate(4));
    }

    /**
     * 获取未来，第past天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(today);
    }

    /**
     * 图表点显示
     */
    private void getAxisPoints() {
        mPointValues = new ArrayList<PointValue>();
        for (int i = 0; i < FutureTemp.size(); i++) {
            mPointValues.add(new PointValue(i, FutureTemp.get(i)));
        }
    }

    /**
     * 初始化图表
     */
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFFFFF"));//折线颜色
        List<Line> lines = new ArrayList<>();
        //折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setValueLabelsTextColor(Color.parseColor("#FFFFFF"));
        data.setValueLabelBackgroundEnabled(false);
        data.setLines(lines);
        futureChart.setInteractive(false);
        futureChart.setZoomType(ZoomType.HORIZONTAL);
        futureChart.setMaxZoom((float) 2);//最大方法比例
        futureChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        futureChart.setLineChartData(data);
        futureChart.setVisibility(View.VISIBLE);
        Viewport v = new Viewport(futureChart.getMaximumViewport());
        v.left = 0;
        v.right = 4;
        futureChart.setCurrentViewport(v);
    }

    @OnClick(R.id.manage)
    public void onViewClicked(View view) {
        Intent intent = new Intent(getContext(), ActaddCity.class);
        startActivityForResult(intent, 12);

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void Event(MessageEvent messageEvent) {
        mcity = messageEvent.getMessage();
        getWeather();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        unbinder.unbind();
    }

}
