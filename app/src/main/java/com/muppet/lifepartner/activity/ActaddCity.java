package com.muppet.lifepartner.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.adapter.McityAdapter;
import com.muppet.lifepartner.mode.MessageEvent;
import com.muppet.lifepartner.util.MySQliteHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActaddCity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    @BindView(R.id.citylist)
    ListView citylist;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.addcity)
    LinearLayout addcity;

    private McityAdapter mcityAdapter;
    private SQLiteDatabase db;
    private List<String> city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_addcity);
        ButterKnife.bind(this);
        mcityAdapter = new McityAdapter(this);
        citylist.setAdapter(mcityAdapter);
        citylist.setOnItemClickListener(this);
        citylist.setOnItemLongClickListener(this);
        initData();
    }
    private void initData() {
        city = new ArrayList<>();
        //查询数据
        /**
         * String table 表名
         * String[] columns 查询的列名
         * , String selection, 筛选条件
         * String[] selectionArgs  筛选值
         * , String groupBy 分组条件
         * , String having 分组值,
         * String orderBy 按...排序
         * litmit  "0,20" "a,b"从第a+1行数据开始，总共查询b条
         */
        MySQliteHelper helper = new MySQliteHelper(this, "Lifepartner.db", null, 1);
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(MySQliteHelper.TABLE_MYCITY, new String[]{"name"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            city.add(name);
        }
        mcityAdapter.setmCity(city);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = city.get(position);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new MessageEvent(name));
        finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int deleteCity = db.delete(MySQliteHelper.TABLE_MYCITY,"name=?",new String[]{city.get(position)});
                if (deleteCity == 0) {
                    Toast.makeText(ActaddCity.this, "删除失败", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("123", "onClick: deleteCity 删除成功"+deleteCity);
                }
                initData();
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

    @OnClick({R.id.back, R.id.addcity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.addcity:
                Intent intent = new Intent(this,ActCityList.class);
                startActivity(intent);
                finish();
                break;
        }
    }


}
