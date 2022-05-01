package com.android.app.android_baraka_demo.data.models

interface Section {
    fun getType(): Int
    fun getLabel(): String

    companion object {
        const val TICKERS: Int = 10
        const val TOP_NEWS = 20
        const val ALL_NEWS = 30
        const val LOADING = 40
        const val ERROR = 50
    }
}