package com.android.app.android_baraka_demo.domain

import com.android.app.android_baraka_demo.data.models.Response
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import kotlinx.coroutines.flow.Flow

class MainUseCase(private val repository: Repository) {
    suspend fun fetchTickersList(): Flow<Response<List<TickerItem>>> {
        return repository.fetchTickersList()
    }

    suspend fun fetchNewsList():Flow<Response<List<NewsItem>>> {
        return repository.fetchNewsList()
    }
}