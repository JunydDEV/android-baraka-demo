package com.android.app.android_baraka_demo.data.network

import com.android.app.android_baraka_demo.data.models.Response
import retrofit2.http.GET
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.data.models.news.response.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getTicketsList(@Url url: String): Flow<Response<List<TickerItem>>>

    @GET
    suspend fun getNewsList(@Url url: String): NewsResponse
}