package com.muppet.lifepartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muppet.lifepartner.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class McityAdapter extends BaseAdapter {
    private Context context;
    private List<String> mcity = new ArrayList<>();

    public McityAdapter(Context context) {
        this.context = context;
    }

    /**
     * 设置数据
     */
    public void setmCity(List<String> city) {
        mcity.clear();
        mcity.addAll(city);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mcity.size();
    }

    @Override
    public Object getItem(int position) {
        return mcity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.weather_citylist_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);//设置标签
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.yourcity.setText(mcity.get(position));
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.yourcity)
        TextView yourcity;
        @BindView(R.id.cityitem)
        LinearLayout cityitem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
