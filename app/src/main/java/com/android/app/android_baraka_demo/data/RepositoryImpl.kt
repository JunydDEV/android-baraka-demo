package com.android.app.android_baraka_demo.data

import com.android.app.android_baraka_demo.data.models.Response
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl : Repository {

    override suspend fun fetchTickersList(): Flow<Response<List<TickerItem>>> = flow {
        emit(Response.Success(getTickerItemsList()))
    }

    override suspend fun fetchNewsList(): Flow<Response<List<NewsItem>>> = flow {
        emit(Response.Success(getALlNewsItemsList()))
    }

    private fun getTickerItemsList(): List<TickerItem> {
        return mutableListOf<TickerItem>().apply {
            add(TickerItem("Tesla1 - 20.00"))
            add(TickerItem("Tesla2 - 20.00"))
            add(TickerItem("Tesla3 - 20.00"))
            add(TickerItem("Tesla4 - 20.00"))
            add(TickerItem("Tesla5 - 20.00"))
            add(TickerItem("Tesla6 - 20.00"))
        }
    }

    private fun getALlNewsItemsList(): List<NewsItem> {
        return mutableListOf<NewsItem>().apply {
            add(NewsItem("image", "News Title", "News Description", "10/20/22"))
            add(NewsItem("image", "News Title", "News Description", "10/20/22"))
            add(NewsItem("image", "News Title", "News Description", "10/20/22"))
            add(NewsItem("image", "News Title", "News Description", "10/20/22"))
            add(NewsItem("image", "News Title", "News Description", "10/20/22"))
        }
    }

}