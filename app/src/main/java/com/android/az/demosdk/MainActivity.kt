package com.android.az.demosdk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.android.az.demosdk.databinding.ActivityMainBinding
import com.android.sdk.miniapp.common.base.BaseActivity
import com.android.sdk.miniapp.config.AZMiniAppRepository
import com.android.sdk.miniapp.config.AZMiniAppSDK
import com.android.sdk.miniapp.config.Action
import com.android.sdk.miniapp.config.model.AZPublicAppModel
import com.android.sdk.miniapp.config.model.toBundle
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    private val miniAppAdapter by lazy {
        MiniAppModelAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding.items.adapter = miniAppAdapter
        miniAppAdapter.setListener(
            listener = { miniApp ->
                miniApp?.let {
                    AZMiniAppSDK.getInstance()
                        .action(Action.ACTION_MINI_APP)
                        .start(this, data = miniApp.toBundle())
                }
            },
            listenerWithId = {miniAppId ->
                miniAppId?.let {
                    AZMiniAppSDK.getInstance()
                        .action(Action.ACTION_MINI_APP)
                        .start(this, data = AZPublicAppModel.idToBundle(it))
                }
            }
        )

        findViewById<View>(R.id.btnStart).setOnClickListener {
            AZMiniAppSDK.getInstance()
                .action(Action.ACTION_OPEN)
                .start(this)
        }

        findViewById<View>(R.id.btnCall).setOnClickListener {
            lifecycleScope.launch {
                val result = AZMiniAppRepository.fetchMiniApps()
                result.onSuccess { data ->
                    miniAppAdapter.updateData(items = data?.toMutableList())
                }.onFailure { exception ->
                    Log.e("TAG", "onCreate: $exception")
                }
            }
        }
    }
}