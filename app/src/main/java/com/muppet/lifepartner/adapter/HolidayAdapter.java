package com.muppet.lifepartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.mode.Holiday;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HolidayAdapter extends BaseAdapter {

    private Context context;
    private List<Holiday.ResultBean.DataBean.HolidayArrayBean> holiday = new ArrayList<>();
    private List<Holiday.ResultBean.DataBean.HolidayArrayBean.ListBean> holidaydate = new ArrayList<>();

    public HolidayAdapter(Context context) {
        this.context = context;
    }

    /**
     * 设置数据
     */
    public void setholidayData(List<Holiday.ResultBean.DataBean.HolidayArrayBean> list) {
        holiday.addAll(list);
        notifyDataSetChanged();//刷新数据
    }
    @Override
    public int getCount() {
        return holiday.size();
    }

    @Override
    public Holiday.ResultBean.DataBean.HolidayArrayBean getItem(int position) {
        return holiday.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calender_holiday, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);//设置标签
        } else {
            vh = (ViewHolder) convertView.getTag();//复用视图
        }
        Holiday.ResultBean.DataBean.HolidayArrayBean holidayArrayBean = getItem(position);
        vh.fangjiaTime.setText(holidayArrayBean.getDesc());
        vh.suggest.setText(holidayArrayBean.getRest());
        vh.festival.setText(holidayArrayBean.getName());
        holidaydate = holidayArrayBean.getList();
        vh.startTime.setText(holidaydate.get(0).getDate());
        vh.endTime.setText(holidaydate.get(holidaydate.size()-1).getDate());
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.fangjia_time)
        TextView fangjiaTime;
        @BindView(R.id.festival)
        TextView festival;
        @BindView(R.id.start_time)
        TextView startTime;
        @BindView(R.id.end_time)
        TextView endTime;
        @BindView(R.id.suggest)
        TextView suggest;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
