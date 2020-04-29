package com.muppet.lifepartner.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.muppet.lifepartner.IpAddress;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.activity.ActExCompany;
import com.muppet.lifepartner.activity.ActExpressInfo;
import com.muppet.lifepartner.adapter.ExHistoryAdapter;
import com.muppet.lifepartner.mode.ExpressInfo;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.MySQliteHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpressPage extends SupportFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @BindView(R.id.expressId)
    EditText expressId;
    @BindView(R.id.ex_company)
    EditText exCompany;
    @BindView(R.id.ex_serch)
    Button exSerch;
    @BindView(R.id.ex_recordList)
    ListView exRecordList;
    Unbinder unbinder;
    @BindView(R.id.tv_text01)
    TextView tvText01;


    private SQLiteDatabase db;
    private String No;//快递公司编号

    private List<String> com;
    private List<String> num;
    private List<String> status;
    private List<String> comid;
    private ExHistoryAdapter exHistoryAdapter;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.express_page, null);
        ButterKnife.bind(this, view);
        MySQliteHelper helper = new MySQliteHelper(getContext(), "Lifepartner.db", null, 1);
        //创建或打开一个可读可写的数据库
        db = helper.getReadableDatabase();
        exHistoryAdapter = new ExHistoryAdapter(getContext());
        exRecordList.setAdapter(exHistoryAdapter);
        exRecordList.setOnItemClickListener(this);
        exRecordList.setOnItemLongClickListener(this);
        inithistory();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    /**
     * 获取快递历史记录列表
     */
    private void inithistory() {
        com = new ArrayList<>();
        num = new ArrayList<>();
        status = new ArrayList<>();
        comid = new ArrayList<>();
        boolean hasdata = false;
        Cursor cursor = db.query(MySQliteHelper.TABLE_EXPRESS, new String[]{"com", "num", "status", "comid"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String mcom = cursor.getString(cursor.getColumnIndex("com"));
            String mnum = cursor.getString(cursor.getColumnIndex("num"));
            String mstatus = cursor.getString(cursor.getColumnIndex("status"));
            String mconid = cursor.getString(cursor.getColumnIndex("comid"));
            com.add(mcom);
            num.add(mnum);
            status.add(mstatus);
            comid.add(mconid);
            hasdata = true;
        }
        if (!hasdata) {
            tvText01.setVisibility(View.VISIBLE);
        } else {
            tvText01.setVisibility(View.GONE);
        }
        exHistoryAdapter.setExhistory(com, num, status, comid);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String No = num.get(position);
        String Com = comid.get(position);
        gethistoryinfo(No, Com);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确认删除");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int deleteNum = db.delete(MySQliteHelper.TABLE_EXPRESS, "num=?", new String[]{num.get(position)});
                if (deleteNum == 0) {
                    Toast.makeText(_mActivity, "删除失败", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("123", "onClick: Database 删除成功"+deleteNum);
                }
                inithistory();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
        return true;
    }

    /**
     * 获取历史记录快递
     *
     * @param no
     * @param com
     */
    private void gethistoryinfo(String no, String com) {
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.EXPRESS,0));
        params.addBodyParameter("key", Constant.EXPRESS_KEY);
        params.addBodyParameter("com", com);
        params.addBodyParameter("no", no);
        x.http().get(params, new Callback.CommonCallback<ExpressInfo>() {
            @Override
            public void onSuccess(ExpressInfo result) {
                ExpressInfo expressInfo = result;
                if (expressInfo.getResult() != null) {
                    //更新快递
                    updateExpress(expressInfo.getResult());
                    //传递对象到下一个活动
                    Intent intent = new Intent(getContext(), ActExpressInfo.class);
                    intent.putExtra("exinfo", expressInfo);
                    startActivity(intent);
                } else {
                    //错误信息提示
                    Toast.makeText(getContext(), expressInfo.getReason(), Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.ex_company, R.id.ex_serch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ex_company:
                Intent intent = new Intent(getContext(), ActExCompany.class);
                startActivityForResult(intent, 11);
                break;
            case R.id.ex_serch:
                Serch();//查询
                break;
        }
    }

    /**
     * 获取输入参数，查询
     */
    private void Serch() {
        String id = expressId.getText().toString();
        String com = exCompany.getText().toString();
        if (id.isEmpty() && com.isEmpty()) {
            Toast.makeText(getContext(), "请输入快递单号和选择快递公司", Toast.LENGTH_SHORT).show();
        } else {
            getexpressinfo(id);//传入参数,请求数据
        }
    }

    /**
     * 获取快递信息
     * @param id
     */
    private void getexpressinfo(String id) {
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.EXPRESS,0));
        params.addBodyParameter("key", Constant.EXPRESS_KEY);
        params.addBodyParameter("com", No);//快递公司编号
        params.addBodyParameter("no", id);//快递单号
        x.http().get(params, new Callback.CommonCallback<ExpressInfo>() {
            @Override
            public void onSuccess(ExpressInfo result) {
                ExpressInfo expressInfo = result;
                if (expressInfo.getResult() != null) {
                    //保存快递
                    saveExpress(expressInfo.getResult());
                    //传递对象到下一个活动
                    Intent intent = new Intent(getContext(), ActExpressInfo.class);
                    intent.putExtra("exinfo", expressInfo);
                    startActivity(intent);
                    inithistory();
                } else {
                    //错误信息提示
                    Toast.makeText(getContext(), expressInfo.getReason(), Toast.LENGTH_SHORT).show();
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
     * 更新已查询到的快递
     *
     * @param result
     */
    private void updateExpress(ExpressInfo.ResultBean result) {
        ContentValues values = new ContentValues();
        if (result.getStatus_detail() == null && result.getStatus().equals("1")) {
            values.put("status", "已完成订单");
        } else if (result.getStatus_detail() == null && result.getStatus().equals("0")) {
            values.put("status", "暂无物流信息");
        } else if (result.getStatus_detail() != null) {
            values.put("status", result.getStatus_detail());
        }
        int num = db.update(MySQliteHelper.TABLE_EXPRESS, values, "num=?", new String[]{result.getNo()});
        if (num == 0) {//num 修改成功的条数
            Log.e("123", "updateExpress: 修改失败");
        } else {
            Log.e("123", "updateExpress: 修改成功" );
        }
    }

    /**
     * 保存已查询到的快递
     *
     * @param result
     */
    private void saveExpress(ExpressInfo.ResultBean result) {
        ContentValues values = new ContentValues();
        values.put("com", result.getCompany());
        values.put("comid", result.getCom());
        values.put("num", result.getNo());
        if (result.getStatus_detail() == null && result.getStatus().equals("1")) {
            values.put("status", "已完成订单");
        } else if (result.getStatus_detail() == null && result.getStatus().equals("0")) {
            values.put("status", "暂无物流信息");
        } else if (result.getStatus_detail() != null) {
            values.put("status", result.getStatus_detail());
        }
        long rowId = db.insert(MySQliteHelper.TABLE_EXPRESS, null, values);
        if (rowId == -1) {
            Log.e("123", "saveExpress: 插入失败");
        } else {
            Log.e("123", "saveExpress: 插入成功");
        }
    }

    /**
     * 获取传回的快递公司信息及id
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 10) {
            if (data != null && data.hasExtra("company")) {
                Bundle bundle = data.getBundleExtra("company");
                String Com = bundle.getString("com");
                No = bundle.getString("no");
                exCompany.setText(Com);
            } else {
                Toast.makeText(getContext(), "哎呀,快递公司不见了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
