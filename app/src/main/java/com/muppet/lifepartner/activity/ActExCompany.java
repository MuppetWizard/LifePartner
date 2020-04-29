package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.muppet.lifepartner.IpAddress;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.adapter.ExCompanyAdapter;
import com.muppet.lifepartner.mode.ExCompany;
import com.muppet.lifepartner.util.App;
import com.muppet.lifepartner.util.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActExCompany extends AppCompatActivity {

    @BindView(R.id.ex_company)
    RecyclerView exCompany;
    @BindView(R.id.back)
    ImageView back;

    private List<ExCompany.ResultBean> companylist;
    private ExCompanyAdapter exCompanyAdapter;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ex_company);
        ButterKnife.bind(this);
        exCompanyAdapter = new ExCompanyAdapter(App.getAppContext());
        exCompany.setAdapter(exCompanyAdapter);
        initRecyclerview();
        getCompany();
    }
    private void initRecyclerview() {
        layoutManager = new LinearLayoutManager(App.getAppContext(), LinearLayoutManager.VERTICAL, false);
        exCompany.setLayoutManager(layoutManager);
        exCompanyAdapter.setOnItemClickListener(new ExCompanyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClik(View view, Object comdata) {
                Intent intent = new Intent();
                intent.putExtra("company", (Bundle) comdata);
                setResult(10, intent);
                finish();
            }
        });
    }

    private void getCompany() {
        companylist = new ArrayList<>();
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.EXP_COM,0));
        params.addBodyParameter("key", Constant.EXPRESS_KEY);
        x.http().get(params, new Callback.CommonCallback<ExCompany>() {
            @Override
            public void onSuccess(ExCompany result) {
                ExCompany exCompany = result;
                if (exCompany.getResult() != null) {
                    companylist = result.getResult();
                    exCompanyAdapter.setCompanylist(companylist);
                } else {
                    Toast.makeText(App.getAppContext(), "暂无数据,请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ActExCompany.this, "请求失败,请检查你的网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
