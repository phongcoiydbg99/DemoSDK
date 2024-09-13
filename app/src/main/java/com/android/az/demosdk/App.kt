package com.android.az.demosdk

import android.app.Application
import com.android.sdk.miniapp.config.AZMiniAppConfig
import com.android.sdk.miniapp.config.AZMiniAppSDK

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = AZMiniAppConfig.AZMiniAppConfigBuilder()
            .setDomain("http://sdk-api.laoapp.io.vn")
            .setGetAccessToken {
                "token"
            }
            .setGetClientId {
                "clientId"
            }
            .build()
        AZMiniAppSDK.create(this)
            .config(config)
            .build()


    }
}