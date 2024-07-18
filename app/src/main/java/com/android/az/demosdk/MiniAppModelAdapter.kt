package com.android.az.demosdk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.az.demosdk.databinding.ItemMiniappModelBinding
import com.android.sdk.miniapp.config.model.AZPublicAppModel
import com.android.sdk.miniapp.extenstions.setImageUrl

class MiniAppModelAdapter(val items: MutableList<AZPublicAppModel> = mutableListOf()) :
    RecyclerView.Adapter<ViewHolder>() {

    private var listener: ((miniApp: AZPublicAppModel?) -> Unit)? = null
    private var listenerWithId: ((miniAppId: String?) -> Unit)? = null


    fun setListener(listener: (miniApp: AZPublicAppModel?) -> Unit, listenerWithId: (miniAppId: String?) -> Unit) {
        this.listener = listener
        this.listenerWithId = listenerWithId
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemMiniappModelBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(items[position], listener, listenerWithId)

    override fun getItemCount() = items.size
    fun updateData(items: MutableList<AZPublicAppModel>?) {
        items?.let {
            this.items.clear()
            this.items.addAll(it)
            notifyDataSetChanged()
        }
    }
}

class ViewHolder(private val binding: ItemMiniappModelBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(
        miniApp: AZPublicAppModel,
        listener: ((miniApp: AZPublicAppModel?) -> Unit)? = null,
        listenerWithId: ((miniAppId: String?) -> Unit)? = null
    ) {
        binding.item.setOnClickListener {
            listener?.invoke(miniApp)
        }
        binding.btnStart.setOnClickListener {
            listenerWithId?.invoke(miniApp.id)
        }
        binding.name.text = miniApp.name
        binding.image.setImageUrl(imageUrl = miniApp.icon)
    }
}

