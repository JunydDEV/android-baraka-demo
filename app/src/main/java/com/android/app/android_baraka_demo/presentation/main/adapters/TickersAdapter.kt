package com.android.app.android_baraka_demo.presentation.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.app.android_baraka_demo.R
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.google.android.material.chip.Chip

class TickersAdapter(
    val tickerItemsList: List<TickerItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_ticker, parent, false)
        return TickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tickerViewHolder = holder as TickerViewHolder
        tickerViewHolder.setTickerLabel()
    }

    override fun getItemCount(): Int {
        return tickerItemsList.size
    }

    inner class TickerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ticker = view as Chip

        fun setTickerLabel(){
            ticker.text = tickerItemsList[adapterPosition].label
        }
    }
}