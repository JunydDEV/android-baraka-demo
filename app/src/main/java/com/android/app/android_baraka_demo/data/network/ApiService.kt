package com.android.app.android_baraka_demo.data.network

import com.android.app.android_baraka_demo.data.models.news.response.NewsResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getTickersList(@Url url: String): Call<ResponseBody>

    @GET
    suspend fun getNewsList(@Url url: String): NewsResponse
}