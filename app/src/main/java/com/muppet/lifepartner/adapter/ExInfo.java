package com.muppet.lifepartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.mode.ExpressInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExInfo extends RecyclerView.Adapter<ExInfo.ViewHolder> {

    private List<ExpressInfo.ResultBean.ListBean> ex_info = new ArrayList<>();
    private Context context;

    public ExInfo(Context context) {
        this.context = context;
    }

    public void setExpressList(List<ExpressInfo.ResultBean.ListBean> exDatas) {
        this.ex_info = exDatas;
        notifyDataSetChanged();//刷新
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.ex_info, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ExpressInfo.ResultBean.ListBean exlist = ex_info.get(position);
        viewHolder.exTime.setText(exlist.getDatetime());
        viewHolder.exMsg.setText(exlist.getRemark());
    }

    @Override
    public int getItemCount() {
        return ex_info.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ex_time)
        TextView exTime;
        @BindView(R.id.ex_msg)
        TextView exMsg;
        @BindView(R.id.ex_item)
        LinearLayout exItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
