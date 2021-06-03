package com.muppet.lifepartner.activity.ad.others

import android.app.Activity
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.muppet.lifepartner.R
import com.muppet.lifepartner.util.Constant
import com.muppet.lifepartner.util.UIUtils

/**
 * des：
 * @author: Muppet
 * @date: 2021/6/1
 */
class SplashController(context: Activity) : FrameLayout(context) {
    private val mContext = context
    private val skipView: TextView
    private var timer: CountDownTimer? = null
    init {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.test_splash_layout,this)
        skipView = mView.findViewById(R.id.yy_tv_skip)
        initView()

    }

    private fun initView() {
        Log.d(Constant.TAG,"init ")

        skipView.setOnClickListener {
            Log.d(Constant.TAG,"skipView clicked")
        }
    }

    fun startTimer(count: Long) {
        timer = object : CountDownTimer(count * 1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                skipView.text =
                        String.format(mContext.resources.getString(com.youyi.yesdk.R.string.yy_str_skip),millisUntilFinished / 1000)
            }

            override fun onFinish() {}
        }
        timer?.start()
    }


    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        Log.d(Constant.TAG,"onViewClick")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(Constant.TAG,"onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(Constant.TAG,"onDetachedFromWindow")
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        super.onVisibilityAggregated(isVisible)
        Log.d(Constant.TAG,"onVisibilityAggregated")
    }


    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (View.VISIBLE == visibility) {
            Log.d(Constant.TAG, "VisibilityStatus is$visibility  VISIBLE")
        } else if (View.GONE == visibility) {
            Log.d(Constant.TAG, "VisibilityStatus is$visibility  GONE")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val  minHeight = UIUtils.getScreenHeight(mContext)
        val minWidth = UIUtils.getScreenWidth(mContext)

        Log.d(Constant.TAG,"width: $widthMeasureSpec , height: $heightMeasureSpec\n screenWidth: $minWidth , height: $minHeight")
    }

}