package com.android.app.android_baraka_demo.data.models.news.response
import com.google.gson.annotations.SerializedName


data class NewsResponse(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("articles") var articles: ArrayList<Articles> = arrayListOf()
)