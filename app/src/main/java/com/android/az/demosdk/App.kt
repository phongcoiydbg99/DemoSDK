package com.android.az.demosdk

import android.app.Application
import com.android.sdk.miniapp.config.MiniApp
import com.android.sdk.miniapp.config.MiniAppConfig

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val config = MiniAppConfig.MiniAppConfigBuilder()
            .deviceId("deviceUniqueID")
            .userId("userId")
            .token("token")
            .domain("http://sdk-api.laoapp.io.vn")
            .build()
        MiniApp.create(this)
            .config(config)
            .build()

    }
}