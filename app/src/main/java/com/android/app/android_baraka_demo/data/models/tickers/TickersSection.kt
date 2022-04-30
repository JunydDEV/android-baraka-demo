package com.android.app.android_baraka_demo.data.models.tickers

import com.android.app.android_baraka_demo.data.models.Section

data class TickersSection(val tickerItemsList: List<TickerItem>) : Section {

    override fun getType(): Int {
        return Section.TICKERS
    }

    override fun getLabel(): String {
        return "Tickers"
    }
}