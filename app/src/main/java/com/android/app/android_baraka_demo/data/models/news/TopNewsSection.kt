package com.android.app.android_baraka_demo.data.models.news

import com.android.app.android_baraka_demo.data.models.Section

class TopNewsSection(val topNewsItemsList: List<NewsItem>) : Section {
    override fun getType(): Int {
        return Section.TOP_NEWS
    }

    override fun getLabel(): String {
        return "Top News"
    }
}