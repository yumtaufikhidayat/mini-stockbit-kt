package com.taufik.ministockbit.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taufik.ministockbit.data.model.Data
import com.taufik.ministockbit.databinding.ItemWatchlistBinding

class MainAdapter : PagingDataAdapter<Data, MainAdapter.MainViewHolder>(WATCHLIST_COMPARATOR) {

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    class MainViewHolder(private val binding: ItemWatchlistBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data){
            binding.apply {
                tvStockName.text = data.coinInfo.name
                tvStockFulName.text = data.coinInfo.fullName

                var price = data.display?.idr?.price
                price = price?.replace("IDR", "")
                tvStockPrice.text = price

                var changePct24Hour = data.display?.idr?.changePct24Hour
                changePct24Hour = changePct24Hour?.replace("-", "")

                tvStockPriceDesc.text = String.format(
                    "%s (%s%%)",
                    data.display?.idr?.changePctDay,
                    changePct24Hour
                )
            }
        }
    }

    companion object {
        private val WATCHLIST_COMPARATOR = object : DiffUtil.ItemCallback<Data>(){
            override fun areItemsTheSame(oldItem: Data, newItem: Data) =
                oldItem.coinInfo.id == newItem.coinInfo.id

            override fun areContentsTheSame(oldItem: Data, newItem: Data) =
                oldItem == newItem
        }
    }
}