package com.android.az.demosdk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.az.demosdk.databinding.ItemMiniappModelBinding
import com.android.sdk.miniapp.config.model.MiniAppModel
import com.android.sdk.miniapp.extenstions.setImageUrl

class MiniAppModelAdapter(val items: MutableList<MiniAppModel> = mutableListOf()) :
    RecyclerView.Adapter<ViewHolder>() {

    private var listener: ((miniApp: MiniAppModel?) -> Unit)? = null
    private var listenerWithId: ((miniAppId: String?) -> Unit)? = null


    fun setListener(listener: (miniApp: MiniAppModel?) -> Unit, listenerWithId: (miniAppId: String?) -> Unit) {
        this.listener = listener
        this.listenerWithId = listenerWithId
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemMiniappModelBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(items[position], listener, listenerWithId)

    override fun getItemCount() = items.size
    fun updateData(items: MutableList<MiniAppModel>?) {
        items?.let {
            this.items.clear()
            this.items.addAll(it)
            notifyDataSetChanged()
        }
    }
}

class ViewHolder(private val binding: ItemMiniappModelBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(
        miniApp: MiniAppModel,
        listener: ((miniApp: MiniAppModel?) -> Unit)? = null,
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

