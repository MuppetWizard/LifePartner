package com.muppet.lifepartner.activity.ad.kt

import org.json.JSONObject

/**
 * des：
 * @author: Muppet
 * @date: 2021/5/6
 */
data class MTSdkManager(
        val appId: String,
        val consent: JSONObject?,

        ){

        class Builder {
            private var id: String = ""
            private var mConsent: JSONObject? = null

            fun appId(id: String): Builder {
                this.id = id
                return this
            }

            fun gdprConsent(consent: JSONObject?): Builder {
                this.mConsent = consent
                return this
            }

            fun build(): MTSdkManager {
                return MTSdkManager(
                        this.id,
                        this.mConsent
                )
            }
        }

}
