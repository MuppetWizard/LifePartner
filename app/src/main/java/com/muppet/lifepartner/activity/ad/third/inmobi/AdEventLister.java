package com.muppet.lifepartner.activity.ad.third.inmobi;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.muppet.lifepartner.util.Constant;

/**
 * des：
 *
 * @author: Muppet
 * @date: 2021/7/5
 */
public class AdEventLister extends NativeAdEventListener {

    private static String TAG = Constant.TAG;
    private ViewGroup container;
    private Context context;


    public AdEventLister(Context context, ViewGroup container) {
        this.container = container;
        this.context = context;
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
            container.addView(
                    inMobiNative.getPrimaryViewOfWidth(
                            context,
                            container,
                            container,
                            container.getWidth()
                    )

            );
//            inMobiNative.getPrimaryViewOfWidthAndHeight(
//                    context,
//                    container,container,container.getWidth(),container.getHeight());
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
