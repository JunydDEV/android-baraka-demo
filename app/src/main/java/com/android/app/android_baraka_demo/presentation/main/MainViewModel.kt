package com.android.app.android_baraka_demo.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.app.android_baraka_demo.data.models.ErrorItem
import com.android.app.android_baraka_demo.data.models.LoadingItem
import com.android.app.android_baraka_demo.data.models.Response
import com.android.app.android_baraka_demo.data.models.Section
import com.android.app.android_baraka_demo.data.models.news.NewsItem
import com.android.app.android_baraka_demo.data.models.news.NewsSection
import com.android.app.android_baraka_demo.data.models.news.TopNewsSection
import com.android.app.android_baraka_demo.data.models.tickers.TickerItem
import com.android.app.android_baraka_demo.data.models.tickers.TickersSection
import com.android.app.android_baraka_demo.di.DependenciesProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val mainUseCase =
        DependenciesProvider.provideMainUseCaseInstance(application.applicationContext)
    private val sectionsList = mutableListOf<Section>()
    private val sectionsListLiveData = MutableLiveData<List<Section>>()
    private val tickerItemsLiveData = MutableLiveData<List<TickerItem>>()

    fun getSectionalsList(): LiveData<List<Section>> {
        return sectionsListLiveData
    }

    fun getTickerItemsLiveData(): LiveData<List<TickerItem>> {
        return tickerItemsLiveData
    }

    private suspend fun getTickersSectionData() {
        mainUseCase.fetchTickersList()
            .onStart {
                showLoadingIndicator()
            }
            .onCompletion {
                getNewsSectionData()
            }
            .catch {
                hideLoadingIndicator()
            }
            .collect {
                when (it) {
                    is Response.Success -> {
                        publishTickersResponse(it)
                    }
                    else -> {
                        publishErrorResponse(it)
                    }
                }
            }
    }

    private suspend fun getNewsSectionData() {
        mainUseCase.fetchNewsList()
            .onCompletion {
                hideLoadingIndicator()
                scheduleStockPricesUpdates()
            }
            .catch {
                hideLoadingIndicator()
            }
            .collect {
                when (it) {
                    is Response.Success -> {
                        publishNewsResponse(it)
                    }
                    else -> {
                        publishErrorResponse(it)
                    }
                }
            }
    }

    private fun hideLoadingIndicator() {
        sectionsList.removeAll { section ->
            section is LoadingItem
        }
        sectionsListLiveData.postValue(sectionsList)
    }

    private fun showLoadingIndicator() {
        sectionsList.clear()
        sectionsList.add(LoadingItem())
        sectionsListLiveData.postValue(sectionsList)
    }

    private fun publishErrorResponse(it: Response<List<Any>>) {
        sectionsList.clear()
        val errorMessage = (it as Response.Error).message
        sectionsList.add(ErrorItem(errorMessage))
        sectionsListLiveData.postValue(sectionsList)
    }

    private fun publishTickersResponse(it: Response.Success<List<TickerItem>>) {
        it.data.let {
            sectionsList.add(TickersSection(it.toMutableList()))
            sectionsListLiveData.postValue(sectionsList)
        }
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

    suspend fun fetchContent() {
        getTickersSectionData()
    }

    private suspend fun getStockPricesUpdates() {
        mainUseCase.fetchTickersList()
            .collect {
                when (it) {
                    is Response.Success -> {
                        it.data.let {newItems->
                            val list = sectionsList.filterIsInstance(TickersSection::class.java).first()
                            list.tickerItemsList.clear()
                            list.tickerItemsList.addAll(newItems)
                            tickerItemsLiveData.postValue(list.tickerItemsList)
                        }
                    }
                    else -> {
                        publishErrorResponse(it)
                    }
                }
            }
    }


    private suspend fun scheduleStockPricesUpdates() {
        while (true) {
            delay(10000L)
            getStockPricesUpdates()
        }
    }
}
