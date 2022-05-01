package com.android.app.android_baraka_demo.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.android.app.android_baraka_demo.data.models.Response
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.news.response.NewsResponse
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.data.network.ApiService
import com.android.app.android_baraka_demo.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.text.DecimalFormat

class RepositoryImpl(private val applicationContext: Context, private val apiService: ApiService) : Repository {

    override suspend fun fetchTickersList(): Flow<Response<List<TickerItem>>> = flow {
        if(!isOnline()){
            emit(Response.Error("Sorry, internet is not available"))
            return@flow
        }

        try {
            val request = apiService.getTickersList(TICKERS_URL)
            val responseString = request.execute().body()?.string()
            parseResponse(responseString)
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: getGeneralErrorMessage()))
        }
    }

    private suspend fun FlowCollector<Response<List<TickerItem>>>.parseResponse(responseString: String?) {
        responseString?.let { response ->
            val list = response
                .split("\n")
                .map { row -> row.split(",") }
                .map { stringsList ->
                    buildTickerItem(stringsList)
                }
            emit(Response.Success(list.filterNotNull()))
        }
    }

    private fun buildTickerItem(list: List<String>): TickerItem? {
        return if(!list.last().contains("PRICE")) {
            val price = list.last().toDouble()
            val decimalFormat = DecimalFormat("#.##")

            return TickerItem("${list.first()}, ${decimalFormat.format(price)} USD")
        } else {
            null
        }
    }

    override suspend fun fetchNewsList(): Flow<Response<List<NewsItem>>> = flow {
        if(!isOnline()){
            emit(Response.Error("Sorry, internet is not available"))
            return@flow
        }

        val response = apiService.getNewsList(NEWS_URL)
        if (response.status == "ok") {
            val newList = getNewsList(response)
            emit(Response.Success(newList))
        } else {
            emit(Response.Error(getGeneralErrorMessage()))
        }
    }

    private fun getGeneralErrorMessage() = "Sorry, something went wrong"

    private fun getNewsList(response: NewsResponse): List<NewsItem> {
        val newList = response.articles.map {
            NewsItem(
                it.urlToImage,
                it.title,
                it.description,
                it.publishedAt
            )
        }
        return newList
    }

    @Suppress("DEPRECATION")
    fun isOnline(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }

    companion object {
        const val TICKERS_URL = "https://raw.githubusercontent.com/dsancov/TestData/main/stocks.csv"
        const val NEWS_URL = "https://saurav.tech/NewsAPI/everything/cnn.json"
    }

}