package com.android.app.android_baraka_demo.data.models
sealed class Response<out T> {
    class Nothing<out T> : Response<T>()
    class Loading<out T> : Response<T>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error<out T>(val message: String) : Response<T>()
}