package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.adapter.ExInfo;
import com.muppet.lifepartner.mode.ExpressInfo;
import com.muppet.lifepartner.App;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActExpressInfo extends AppCompatActivity {

    @BindView(R.id.ic_company)
    ImageView icCompany;
    @BindView(R.id.ex_status)
    TextView exStatus;
    @BindView(R.id.info_com)
    TextView infoCom;
    @BindView(R.id.info_id)
    TextView infoId;
    @BindView(R.id.ex_list)
    RecyclerView exList;
    @BindView(R.id.back)
    ImageView back;

    private List<ExpressInfo.ResultBean.ListBean> ex_list;
    private ExInfo exInfo;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_express_info);
        ButterKnife.bind(this);
        initData();
    }
    private void initData() {
        Intent intent = getIntent();
        //初始化RecyclerView
        exInfo = new ExInfo(App.getAppContext());
        exList.setAdapter(exInfo);
        layoutManager = new LinearLayoutManager(App.getAppContext(), LinearLayoutManager.VERTICAL, false);
        exList.setLayoutManager(layoutManager);
        if (intent != null && intent.hasExtra("exinfo")) {
            ExpressInfo expressInfo = (ExpressInfo) intent.getSerializableExtra("exinfo");
            setData(expressInfo);
            ex_list = expressInfo.getResult().getList();
            Collections.reverse(ex_list);
            exInfo.setExpressList(ex_list);
        }
    }

    private void setData(ExpressInfo info) {
        ExpressInfo.ResultBean basicsinfo = info.getResult();
        infoCom.setText(basicsinfo.getCompany());//快递公司
        infoId.setText(basicsinfo.getNo());//快递编号
        //快递状态
        String status = basicsinfo.getStatus_detail();
        if (status == null && basicsinfo.getStatus().equals("0")) {
            exStatus.setText("暂无物流信息");
        } else if (status == null && basicsinfo.getStatus().equals("1")) {
            exStatus.setText("已完成订单");
        } else if (status.equals("PENDING")) {
            exStatus.setText("待查询");
        } else if (status.equals("NO_RECORD")) {
            exStatus.setText("无记录");
        } else if (status.equals("ERROR")) {
            exStatus.setText("查询异常");
        } else if (status.equals("IN_TRANSIT")) {
            exStatus.setText("运输中");
        } else if (status.equals("DELIVERING")) {
            exStatus.setText("派送中");
        } else if (status.equals("SIGNED")) {
            exStatus.setText("已签收");
        } else if (status.equals("REJECTED")) {
            exStatus.setText("拒签");
        } else if (status.equals("PROBLEM")) {
            exStatus.setText("疑难件");
        } else if (status.equals("INVALID")) {
            exStatus.setText("无效件");
        } else if (status.equals("TIMEOUT")) {
            exStatus.setText("超时件");
        } else if (status.equals("FAILED")) {
            exStatus.setText("派送失败");
        } else if (status.equals("SEND_BACK")) {
            exStatus.setText("退回");
        } else if (status.equals("TAKING")) {
            exStatus.setText("揽件");
        }
        String com = basicsinfo.getCom();
        if (com.equals("sf")) {
            icCompany.setImageResource(R.mipmap.sf);
        } else if (com.equals("yt")) {
            icCompany.setImageResource(R.mipmap.yt);
        } else if (com.equals("yd")) {
            icCompany.setImageResource(R.mipmap.yd);
        } else if (com.equals("tt")) {
            icCompany.setImageResource(R.mipmap.tt);
        } else if (com.equals("ems")) {
            icCompany.setImageResource(R.mipmap.yz);
        } else if (com.equals("zto")) {
            icCompany.setImageResource(R.mipmap.zt);
        } else if (com.equals("ane66")) {
            icCompany.setImageResource(R.mipmap.an);
        } else if (com.equals("bsky")) {
            icCompany.setImageResource(R.mipmap.bs);
        } else {
            icCompany.setImageResource(R.mipmap.express);
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
