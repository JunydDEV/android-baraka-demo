package com.android.app.android_baraka_demo.data

import com.android.app.android_baraka_demo.data.models.Response
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.data.network.ApiService
import com.android.app.android_baraka_demo.domain.Repository
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

class RepositoryImpl(private val apiService: ApiService) : Repository {

    override suspend fun fetchTickersList(): Flow<Response<List<TickerItem>>> = flow {
        apiService.getTicketsList(TICKERS_URL)
            .onStart {
                emit(Response.Loading())
            }
            .catch {
                emit(Response.Error("Fetching tickers failed.."))
            }
            .collect {
                emit(it)
            }
    }

    override suspend fun fetchNewsList(): Flow<Response<List<NewsItem>>> = flow {
        val response = apiService.getNewsList(NEWS_URL)
        if (response.status == "ok") {
            val newList = response.articles.map {
                NewsItem(
                    it.urlToImage,
                    it.title,
                    it.description,
                    it.publishedAt
                )
            }
            emit(Response.Success(newList))
        } else {
            emit(Response.Error("Oops, something went wrong"))
        }
    }

    companion object {
        const val TICKERS_URL = "https://raw.githubusercontent.com/dsancov/TestData/main/stocks.csv"
        const val NEWS_URL = "https://saurav.tech/NewsAPI/everything/cnn.json"
    }

}