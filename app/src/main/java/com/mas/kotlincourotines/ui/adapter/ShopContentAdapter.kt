package com.mas.kotlincourotines.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mas.kotlincourotines.data.model.ShopContent
import com.mas.kotlincourotines.databinding.ItemShopContentBinding

class ShopContentAdapter(
    private val shopContents: List<ShopContent>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ShopContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShopContentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopContent = shopContents[position]
        holder.bind(shopContent)
    }

    override fun getItemCount(): Int = shopContents.size

    inner class ViewHolder(
        private val binding: ItemShopContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shopContent: ShopContent) {
            binding.shopContent = shopContent
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(shopContent)
            }
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(shopContent: ShopContent)
    }
}