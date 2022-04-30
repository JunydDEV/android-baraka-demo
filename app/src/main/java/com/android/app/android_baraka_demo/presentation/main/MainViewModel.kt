package com.android.app.android_baraka_demo.presentation.main

import androidx.lifecycle.ViewModel
import com.android.app.android_baraka_demo.data.models.Section
import com.android.app.android_baraka_demo.data.models.news.NewsSection
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.news.TopNewsSection
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.data.models.tickers.TickersSection

class MainViewModel : ViewModel(){
    fun getSectionalsList(): List<Section> {
        return mutableListOf<Section>().apply {
            add(getTickersSection())
            add(getTopNewsSection())
            add(getAllNewsSection())
        }
    }

    private fun getTickersSection(): Section {
        return TickersSection(getTickerItemsList())
    }

    private fun getTickerItemsList(): List<TickerItem> {
        return mutableListOf<TickerItem>().apply {
            add(TickerItem("Tesla - 20.00"))
            add(TickerItem("Tesla - 20.00"))
            add(TickerItem("Tesla - 20.00"))
            add(TickerItem("Tesla - 20.00"))
        }
    }

    private fun getTopNewsSection(): Section {
        return TopNewsSection(getTopNewsItemsList())
    }

    private fun getTopNewsItemsList(): List<NewsItem> {
        return mutableListOf<NewsItem>().apply {
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
        }
    }

    private fun getAllNewsSection(): NewsSection {
        return NewsSection(getALlNewsItemsList())
    }

    private fun getALlNewsItemsList(): List<NewsItem> {
        return mutableListOf<NewsItem>().apply {
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
            add(NewsItem("image","News Title", "News Description", "10/20/22"))
        }
    }
}
