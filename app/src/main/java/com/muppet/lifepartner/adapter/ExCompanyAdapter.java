package com.muppet.lifepartner.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.mode.ExCompany;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ExCompanyAdapter extends RecyclerView.Adapter<ExCompanyAdapter.VH> {

    private Context context;
    private List<ExCompany.ResultBean> companylist = new ArrayList<>();
    private ExCompanyAdapter.OnItemClickListener OnItemClickListener;

    public ExCompanyAdapter(Context context) {
        this.context = context;
    }

    //设置数据
    public void setCompanylist(List<ExCompany.ResultBean> list) {
        this.companylist = list;
        notifyDataSetChanged();//刷新
    }


    @Override
    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ex_company, viewGroup, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH vh, int position) {
        final ExCompany.ResultBean companys = companylist.get(position);
        vh.company.setText(companys.getCom());

        //3,对recyclerview的每一个itemview设置点击事件
        vh.companyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnItemClickListener != null) {
                    String com = companys.getCom();
                    String no = companys.getNo();
                    //传递一捆数据
                    Bundle bundle = new Bundle();
                    bundle.putString("com", com);
                    bundle.putString("no", no);
                    OnItemClickListener.OnItemClik(vh.companyItem, bundle);
                }
            }
        });
        String Comic = companys.getNo();
        if (Comic.equals("sf")) {
            vh.icExcom.setImageResource(R.mipmap.sf);
        } else if (Comic.equals("yt")) {
            vh.icExcom.setImageResource(R.mipmap.yt);
        } else if (Comic.equals("yd")) {
            vh.icExcom.setImageResource(R.mipmap.yd);
        } else if (Comic.equals("tt")) {
            vh.icExcom.setImageResource(R.mipmap.tt);
        } else if (Comic.equals("ems")) {
            vh.icExcom.setImageResource(R.mipmap.yz);
        } else if (Comic.equals("zto")) {
            vh.icExcom.setImageResource(R.mipmap.zt);
        } else if (Comic.equals("ane66")) {
            vh.icExcom.setImageResource(R.mipmap.an);
        } else if (Comic.equals("bsky")) {
            vh.icExcom.setImageResource(R.mipmap.bs);
        } else {
            vh.icExcom.setImageResource(R.mipmap.express);
        }
    }

    @Override
    public int getItemCount() {
        return companylist.size();
    }

    //实现点击事件
    //1,定义回调接口
    public interface OnItemClickListener {
        void OnItemClik(View view, Object comdata);
    }

    //定义一个设置点击监听器的方法
    public void setOnItemClickListener(ExCompanyAdapter.OnItemClickListener listener) {
        this.OnItemClickListener = listener;
    }

    static
    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.company)
        TextView company;
        @BindView(R.id.company_item)
        LinearLayout companyItem;
        @BindView(R.id.ic_excom)
        ImageView icExcom;
        VH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
