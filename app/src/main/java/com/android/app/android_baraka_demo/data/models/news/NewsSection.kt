package com.android.app.android_baraka_demo.data.models.news

import com.android.app.android_baraka_demo.data.models.Section

class NewsSection(val newsItemsList: List<NewsItem>) : Section {
    override fun getType(): Int {
        return Section.ALL_NEWS
    }

    override fun getLabel(): String {
        return "All News"
    }
}