package com.android.app.android_baraka_demo.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.demo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}