package com.muppet.lifepartner.activity.ad.third.inmobi;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;

/**
 * des：
 *
 * @author: Muppet
 * @date: 2021/6/24
 */
public class NativeAdListener extends NativeAdEventListener {

    private String TAG = NativeAdEventListener.class.getSimpleName();

    private Context mContext;
    private ViewGroup mContainer;

    public NativeAdListener(Context context, ViewGroup container) {
        this.mContext = context;
        this.mContainer = container;
    }

    @Override
    public void onAdReceived(InMobiNative inMobiNative) {
        super.onAdReceived(inMobiNative);
        Log.d(TAG,"onAdReceived");
    }

    @Override
    public void onAdLoadSucceeded(InMobiNative inMobiNative) {
        super.onAdLoadSucceeded(inMobiNative);
        Log.d(TAG,"onAdLoadSucceeded");
        if (inMobiNative.isReady()) {
//                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(container.getWidth(),100 );
            mContainer.addView(
                    inMobiNative.getPrimaryViewOfWidth(
                            mContext,
                            mContainer,
                            mContainer,
                            mContainer.getWidth()
                    )

            );
           /* inMobiNative.getPrimaryViewOfWidthAndHeight(
                    mContext,
                    mContainer,mContainer,mContainer.getWidth(),mContainer.getHeight());*/
        }
    }

    @Override
    public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus status) {
        super.onAdLoadFailed(inMobiNative, status);
        Log.d(TAG,"onAdLoadFailed: "+status.getMessage()+" code: "+status.getStatusCode());
    }

    @Override
    public void onAdFullScreenDismissed(InMobiNative inMobiNative) {
        super.onAdFullScreenDismissed(inMobiNative);
        Log.d(TAG,"onAdFullScreenDismissed");
    }

    @Override
    public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {
        super.onAdFullScreenWillDisplay(inMobiNative);
        Log.d(TAG,"onAdFullScreenWillDisplay");
    }

    @Override
    public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {
        super.onAdFullScreenDisplayed(inMobiNative);
        Log.d(TAG,"onAdFullScreenDisplayed");
    }

    @Override
    public void onUserWillLeaveApplication(InMobiNative inMobiNative) {
        super.onUserWillLeaveApplication(inMobiNative);
        Log.d(TAG,"onUserWillLeaveApplication");
    }

    @Override
    public void onAdImpressed(InMobiNative inMobiNative) {
        super.onAdImpressed(inMobiNative);
        Log.d(TAG,"onAdImpressed");
    }

    @Override
    public void onAdClicked(InMobiNative inMobiNative) {
        super.onAdClicked(inMobiNative);
        Log.d(TAG,"onAdClicked");
    }

    @Override
    public void onAdStatusChanged(InMobiNative inMobiNative) {
        super.onAdStatusChanged(inMobiNative);
        Log.d(TAG,"onAdStatusChanged");
    }

    @Override
    public void onRequestPayloadCreated(byte[] bytes) {
        super.onRequestPayloadCreated(bytes);
        Log.d(TAG,"onRequestPayloadCreated");
    }

    @Override
    public void onRequestPayloadCreationFailed(InMobiAdRequestStatus status) {
        super.onRequestPayloadCreationFailed(status);
        Log.d(TAG,"onRequestPayloadCreationFailed: "+status.getMessage()+" code: "+status.getStatusCode());
    }
}
