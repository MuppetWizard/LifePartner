package com.muppet.lifepartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muppet.lifepartner.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<String> com = new ArrayList<>();
    private List<String> num = new ArrayList<>();
    private List<String> status = new ArrayList<>();
    private List<String> comId = new ArrayList<>();

    public ExHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setExhistory(List<String> com, List<String> num, List<String> status, List<String> comid) {
        this.com = com;
        this.num = num;
        this.status = status;
        this.comId = comid;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return com.size();
    }

    @Override
    public Object getItem(int position) {
        return com.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.express_record_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);//设置标签
        } else {
            vh = (ViewHolder) convertView.getTag();//复用视图
        }
        vh.exCom.setText(com.get(position));
        vh.exNum.setText(num.get(position));
        //快递状态
        String mstatus = status.get(position);
        if (mstatus.equals("已完成订单")) {
            vh.exReStatus.setText("已完成订单");
        } else if (mstatus.equals("暂无物流信息")) {
            vh.exReStatus.setText("暂无物流信息");
        } else if (mstatus.equals("PENDING")) {
            vh.exReStatus.setText("待查询");
        } else if (mstatus.equals("NO_RECORD")) {
            vh.exReStatus.setText("无记录");
        } else if (mstatus.equals("ERROR")) {
            vh.exReStatus.setText("查询异常");
        } else if (mstatus.equals("IN_TRANSIT")) {
            vh.exReStatus.setText("运输中");
        } else if (mstatus.equals("DELIVERING")) {
            vh.exReStatus.setText("派送中");
        } else if (mstatus.equals("SIGNED")) {
            vh.exReStatus.setText("已签收");
        } else if (mstatus.equals("REJECTED")) {
            vh.exReStatus.setText("拒签");
        } else if (mstatus.equals("PROBLEM")) {
            vh.exReStatus.setText("疑难件");
        } else if (mstatus.equals("INVALID")) {
            vh.exReStatus.setText("无效件");
        } else if (mstatus.equals("TIMEOUT")) {
            vh.exReStatus.setText("超时件");
        } else if (mstatus.equals("FAILED")) {
            vh.exReStatus.setText("派送失败");
        } else if (mstatus.equals("SEND_BACK")) {
            vh.exReStatus.setText("退回");
        } else if (mstatus.equals("TAKING")) {
            vh.exReStatus.setText("揽件");
        }
        //快递图片
        String comid = comId.get(position);
        if (comid.equals("sf")) {
            vh.icCom.setImageResource(R.mipmap.sf);
        } else if (comid.equals("yt")) {
            vh.icCom.setImageResource(R.mipmap.yt);
        }else if (comid.equals("yd")) {
            vh.icCom.setImageResource(R.mipmap.yd);
        }else if (comid.equals("tt")) {
            vh.icCom.setImageResource(R.mipmap.tt);
        }else if (comid.equals("ems")) {
            vh.icCom.setImageResource(R.mipmap.yz);
        }else if (comid.equals("zto")) {
            vh.icCom.setImageResource(R.mipmap.zt);
        }else if (comid.equals("ane66")) {
            vh.icCom.setImageResource(R.mipmap.an);
        }else if (comid.equals("bsky")) {
            vh.icCom.setImageResource(R.mipmap.bs);
        }
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.ic_com)
        ImageView icCom;
        @BindView(R.id.ex_com)
        TextView exCom;
        @BindView(R.id.ex_num)
        TextView exNum;
        @BindView(R.id.ex_re_status)
        TextView exReStatus;
        @BindView(R.id.recordItem)
        LinearLayout recordItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
