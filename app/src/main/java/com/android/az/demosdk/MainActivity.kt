package com.android.az.demosdk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.android.az.demosdk.databinding.ActivityMainBinding
import com.android.sdk.miniapp.common.base.BaseActivity
import com.android.sdk.miniapp.config.Action
import com.android.sdk.miniapp.config.MiniApp
import com.android.sdk.miniapp.config.MiniAppRepository
import com.android.sdk.miniapp.config.model.MiniAppModel
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
            listener = {miniApp ->
                miniApp?.let {
                    MiniApp.getInstance()
                        .action(Action.ACTION_MINI_APP)
                        .start(this, data = it.toBundle())
                }
            },
            listenerWithId = {miniAppId ->
                miniAppId?.let {
                    MiniApp.getInstance()
                        .action(Action.ACTION_MINI_APP)
                        .start(this, data = MiniAppModel.idToBundle(it))
                }
            }
        )

        findViewById<View>(R.id.btnStart).setOnClickListener {
            MiniApp.getInstance()
                .action(Action.ACTION_OPEN)
                .start(this)
        }

        findViewById<View>(R.id.btnCall).setOnClickListener {
            lifecycleScope.launch {
                val result = MiniAppRepository.fetchMiniApps()
                result.onSuccess { data ->
                    miniAppAdapter.updateData(items = data.toMutableList())
                }.onFailure { exception ->
                    Log.e("TAG", "onCreate: $exception")
                }
            }
        }
    }
}