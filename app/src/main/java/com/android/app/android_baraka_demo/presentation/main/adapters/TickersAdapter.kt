package com.android.app.android_baraka_demo.presentation.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.app.android_baraka_demo.R
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.google.android.material.chip.Chip

class TickersAdapter : ListAdapter<TickerItem, RecyclerView.ViewHolder>(FeatureDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_ticker, parent, false)
        return TickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tickerViewHolder = holder as TickerViewHolder
        tickerViewHolder.setTickerLabel()
    }

    inner class TickerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ticker = view as Chip

        fun setTickerLabel() {
            with(getItem(adapterPosition)) {
                val text = "$label, $price $currency"
                ticker.text = text
            }
        }
    }

    class FeatureDiffCallback : DiffUtil.ItemCallback<TickerItem>() {
        override fun areItemsTheSame(oldItem: TickerItem, newItem: TickerItem): Boolean =
            oldItem.label == newItem.label

        override fun areContentsTheSame(oldItem: TickerItem, newItem: TickerItem): Boolean =
            oldItem == newItem
    }

    companion object {
        const val DELAY_BETWEEN_SCROLL_MS = 50L
        const val SCROLL_DX = 5
        const val DIRECTION_RIGHT = 1
    }
}