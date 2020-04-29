package com.muppet.lifepartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.muppet.lifepartner.R;
import com.muppet.lifepartner.mode.LetterCityMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mcontext;
    private List<LetterCityMode> mData;
    private OnItemClickListener mOnItemClickListener;

    public CityAdapter(Context context, List<LetterCityMode> mData) {
        mInflater = LayoutInflater.from(context);
        this.mcontext = context;
        this.mData = mData;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.city_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( final ViewHolder viewHolder, final int position) {
        /*if (mOnItemClickListener != null) {
            viewHolder.tvCityitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(viewHolder.tvCityitem,position);
                }
            });

        }*/
        viewHolder.tvCityitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(viewHolder.tvCityitem,mData.get(position).getName());
                }
            }
        });

        viewHolder.tvName.setText(mData.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, Object name);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    //**************************************************************

    /**
     * 提供给Activity刷新数据
     *
     * @param list
     */
    public void updateList(List<LetterCityMode> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    //**********************************************

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tv_cityitem)
        LinearLayout tvCityitem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
