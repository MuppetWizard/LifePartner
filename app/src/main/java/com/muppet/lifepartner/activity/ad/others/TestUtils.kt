package com.muppet.lifepartner.activity.ad.others

/**
 * des：
 * @author: Muppet
 * @date: 2021/6/7
 */
object TestUtils {

    fun testFor(str: String) : String? {
        val mStr = str.split("|")
        return mStr[1] + mStr[0]
    }
}