package com.muppet.lifepartner.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.youyi.yesdk.R
import com.youyi.yesdk.comm.UERepository
import com.youyi.yesdk.comm.event.UENetworkResult

/**
 * des：
 * @author: Muppet
 * @date: 2021/6/2
 */
class YYBannerView(context: Context) : RelativeLayout(context)  {

    private val mContext = context
    private val ivBanner: ImageView
    private val yyCancel: ImageView
    private val yyLogo: TextView

//    private lateinit var mAdListener: BannerAdListener
//    private lateinit var mEvent: YYCoreBannerListener.YYCoreBusinessEvent

    init {
        val view = View.inflate(mContext, R.layout.yy_banner,this)
        ivBanner = view.findViewById(R.id.yy_iv_banner)
        yyCancel = view.findViewById(R.id.yy_iv_cancel)
        yyLogo = view.findViewById(R.id.yy_logo)
        initView()
    }

    private fun initView() {
        yyCancel.visibility = View.GONE
        yyLogo.visibility = View.GONE

    }

     fun setConfig(width: Float, height: Float) {
        val layoutParams = ViewGroup.LayoutParams(width.toInt(),height.toInt())
        this.layoutParams = layoutParams

    }



     fun loadResource(imgUrl: String, link: String) {
        UERepository.getBitMap(imgUrl,3500,object : UENetworkResult<Bitmap>{
            override fun onSuccess(result: Bitmap?) {
                ivBanner.setImageBitmap(result)
                yyCancel.visibility = View.VISIBLE
                yyLogo.visibility = View.VISIBLE
//                mAdListener.onLoaded(this@YYBannerView)

            }

            override fun onFailed(errorCode: Int, e: Exception?) {
//                mAdListener.onError(errorCode,"Ad loading failed")
                e?.printStackTrace()
            }
        })
    }

     fun destroy() {
        recycleResource(ivBanner)
    }

    private fun recycleResource(iv: ImageView?) {
        //release bitmap resource
        val bitmapDrawable = iv?.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        if (bitmap != null && !bitmap.isRecycled) {
            bitmap.recycle()
        }

        //release background resource
        /*val bitmapDrawableBackground = iv.background as BitmapDrawable
        val backgroundBitmap = bitmapDrawableBackground.bitmap
        if (backgroundBitmap != null && !backgroundBitmap.isRecycled) {
            bitmap.recycle()
        }*/
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE){
//            mAdListener.onShow()
        }
    }


}