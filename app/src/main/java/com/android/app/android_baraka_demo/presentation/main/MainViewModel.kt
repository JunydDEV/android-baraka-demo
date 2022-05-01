package com.android.app.android_baraka_demo.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.app.android_baraka_demo.data.models.ErrorItem
import com.android.app.android_baraka_demo.data.models.LoadingItem
import com.android.app.android_baraka_demo.data.models.Response
import com.android.app.android_baraka_demo.data.models.Section
import com.android.app.android_baraka_demo.data.models.news.NewsSection
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.news.TopNewsSection
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.data.models.tickers.TickersSection
import com.android.app.android_baraka_demo.di.DependenciesProvider

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val mainUseCase =
        DependenciesProvider.provideMainUseCaseInstance(application.applicationContext)
    private val sectionsList = mutableListOf<Section>()
    private val sectionsListLiveData = MutableLiveData<List<Section>>()

    fun getSectionalsList(): LiveData<List<Section>> {
        return sectionsListLiveData
    }

    suspend fun getTickersSection() {
        mainUseCase.fetchTickersList().collect {
            if(it is Response.Success) {
                publishTickersResponse(it)
            }
        }
    }

    suspend fun getNewsSection() {
        mainUseCase.fetchNewsList().collect {
            when (it) {
                is Response.Loading -> {
                    publishLoadingResponse()
                }
                is Response.Success -> {
                    publishNewsResponse(it)
                }
                else -> {
                    publishErrorResponse(it)
                }
            }
        }
    }

    private fun publishLoadingResponse() {
        sectionsList.clear() // To remove the error or other data
        sectionsList.add(LoadingItem())
        sectionsListLiveData.postValue(sectionsList)
    }

    private fun publishErrorResponse(it: Response<List<NewsItem>>) {
        sectionsList.clear() // To remove the loading indicator or other data
        val errorMessage = (it as Response.Error).message
        sectionsList.add(ErrorItem(errorMessage))
        sectionsListLiveData.postValue(sectionsList)
    }

    private fun publishTickersResponse(it: Response.Success<List<TickerItem>>) {
        it.data.let {
            sectionsList.add(TickersSection(it))
        }
        sectionsListLiveData.postValue(sectionsList)
    }

    private fun publishNewsResponse(it: Response.Success<List<NewsItem>>) {
        it.data.let {
            sectionsList.clear() // To remove the loading indicator or error item
            getTopNews(it)
            sectionsList.add(NewsSection(it))
        }
        sectionsListLiveData.postValue(sectionsList)
    }

    private fun getTopNews(it: List<NewsItem>) {
        val listOfTopSixNews = it.subList(0, 6)
        sectionsList.add(TopNewsSection(listOfTopSixNews))
    }
}
