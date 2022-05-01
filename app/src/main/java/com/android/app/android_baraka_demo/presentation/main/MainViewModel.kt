package com.android.app.android_baraka_demo.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.app.android_baraka_demo.data.models.Response
import com.android.app.android_baraka_demo.data.models.Section
import com.android.app.android_baraka_demo.data.models.news.NewsSection
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.news.TopNewsSection
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.data.models.tickers.TickersSection
import com.android.app.android_baraka_demo.di.DependenciesProvider

class MainViewModel : ViewModel() {
    private val mainUseCase = DependenciesProvider.provideMainUseCaseInstance()
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
            if(it is Response.Success) {
                publishNewsResponse(it)
            }
        }
    }

    private fun publishTickersResponse(it: Response.Success<List<TickerItem>>) {
        it.data.let {
            sectionsList.add(TickersSection(it))
        }
        sectionsListLiveData.postValue(sectionsList)
    }

    private fun publishNewsResponse(it: Response.Success<List<NewsItem>>) {
        it.data.let {
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
