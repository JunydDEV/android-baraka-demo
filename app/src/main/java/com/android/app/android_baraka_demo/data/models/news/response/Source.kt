package com.android.app.android_baraka_demo.data.models.news.response

import com.google.gson.annotations.SerializedName


data class Source(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null
)