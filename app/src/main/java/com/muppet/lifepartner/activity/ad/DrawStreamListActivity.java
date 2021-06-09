package com.muppet.lifepartner.activity.ad;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.muppet.lifepartner.R;
import com.muppet.lifepartner.util.Constant;
import com.muppet.lifepartner.util.UIUtils;
import com.youyi.yesdk.ad.DrawStreamAd;
import com.youyi.yesdk.business.AdPlacement;
import com.youyi.yesdk.listener.StreamAdExpress;
import com.youyi.yesdk.listener.StreamAdInteractionListener;
import com.youyi.yesdk.listener.StreamAdListener;
import com.youyi.yesdk.listener.UEVideoListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawStreamListActivity extends AppCompatActivity {

    @BindView(R.id.rl_ad)
    RecyclerView rlAd;

    private Context mContext;
    private MyAdapter myAdapter;

    private StreamAdExpress adInfo;

    private ViewPagerLayoutManager mLayoutManager;


    private List<Item> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_stream_list);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        loadExpressDrawNativeAd();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLayoutManager != null) {
            mLayoutManager.setOnViewPagerListener(null);
        }

    }

    private void initView() {

//        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        PagerSnapHelper helper = new PagerSnapHelper();
//        helper.attachToRecyclerView(rlAd);
        mLayoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        myAdapter = new MyAdapter(datas );
        rlAd.setLayoutManager(mLayoutManager);
        rlAd.setAdapter(myAdapter);

        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(Constant.TAG, "释放位置:" + position + " 下一页:" + isNext);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.e(Constant.TAG, "选中位置:" + position + "  是否是滑动到底部:" + isBottom);
            }
        });
    }

    private void loadExpressDrawNativeAd() {
        float expressViewWidth = UIUtils.getScreenWidthDp(this);
        float expressViewHeight = UIUtils.getHeight(this);
        DrawStreamAd drawStreamAd = new DrawStreamAd();
        drawStreamAd.setDrawStreamConfig(this,
                new AdPlacement.Builder()
                        .setAdId("0000000063")
                        .setAdCount(3)
                        .setCanInterruptVideoPlay(true)
                        .setExpressViewAcceptedSize(expressViewWidth,expressViewHeight)
                        .build());
        drawStreamAd.loadDrawStreamAd( new StreamAdListener() {
            @Override
            public void onError(@Nullable Integer integer, @Nullable String s) {
                Log.d(Constant.TAG,"onError : code: "+ integer+" msg : "+ s);
            }

            @Override
            public void onAdLoaded(@NotNull ArrayList<StreamAdExpress> arrayList) {
                for (StreamAdExpress ad : arrayList) {
                    bindInteractionListener(ad);
                    bindVideoAdListener(ad);
//                    ad.render();
                }
            }
        });
    }

    private void bindInteractionListener(StreamAdExpress ad) {
        ad.setStreamAdInteractionListener(new StreamAdInteractionListener() {
            @Override
            public void onAdClicked() {
                Log.d(Constant.TAG,"onAdClicked ");
            }

            @Override
            public void onAdShow() {
                Log.d(Constant.TAG,"onAdShow ");
            }

            @Override
            public void onRenderSuccess() {
                Log.d(Constant.TAG,"onRenderSuccess ");
                datas.add(new Item(ad,-1,-1));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRenderFailed(@Nullable Integer integer, @Nullable String s) {
                Log.d(Constant.TAG,"onRenderFailed: "+ integer + " msg:"+s);
            }
        });
    }

    private void bindVideoAdListener(StreamAdExpress ad) {
        ad.setStreamVideoAdListener(new UEVideoListener() {
            @Override
            public void onVideoAdLoad() {
                Log.d(Constant.TAG,"onVideoAdLoad ");
            }

            @Override
            public void onVideoAdError(int i, int i1) {
                Log.d(Constant.TAG,"onVideoAdError: errorCode: "+i+" extraCode: "+i1);
            }

            @Override
            public void onVideoAdStartPlay() {
                Log.d(Constant.TAG,"onVideoAdStartPlay ");
            }

            @Override
            public void onVideoAdPaused() {
                Log.d(Constant.TAG,"onVideoAdPaused ");
            }

            @Override
            public void onVideoAdContinuePlay() {
                Log.d(Constant.TAG,"onVideoAdContinuePlay ");
            }

            @Override
            public void onProgressUpdate(long current, long duration) {
                Log.d(Constant.TAG,"onProgressUpdate current:" +current + " duration: " +duration);
            }

            @Override
            public void onVideoAdComplete() {
                Log.d(Constant.TAG,"onVideoAdComplete ");
            }

            @Override
            public void onClickRetry() {
                Log.d(Constant.TAG,"onClickRetry ");
            }
        });
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Item> datas;

        public MyAdapter(List<Item> data) {
            this.datas = data;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_pager, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            View view = new View(mContext);
            Item item = null ;
            if (datas != null) {
                item = datas.get(holder.getAdapterPosition());
                if (item.ad != null) {
                    view = item.ad.getStreamView();
                }
                Log.e(Constant.TAG,"size "+datas.size() );
            }
            holder.flVideoContainer.removeAllViews();
            if(view.getParent()!=null){
                ((ViewGroup)view.getParent()).removeView(view);
            }
            holder.flVideoContainer.addView(view);
            Log.e(Constant.TAG,"position "+ holder.getAdapterPosition());
            if (item != null) {

            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            FrameLayout flVideoContainer;
            LinearLayout verticalIconLauout;

            public ViewHolder(View itemView) {
                super(itemView);
                flVideoContainer = itemView.findViewById(R.id.fl_video_container);
                verticalIconLauout = itemView.findViewById(R.id.vertical_icon);
            }
        }
    }

    private static class Item {
        public StreamAdExpress ad;
        public int videoId;
        public int ImgId;

        public Item(StreamAdExpress ad, int videoId, int imgId) {
            this.ad = ad;
            this.videoId = videoId;
            ImgId = imgId;
        }
    }
}