package com.android.app.android_baraka_demo.presentation.main

import android.util.Log
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
    private val _sectionsListLiveData = MutableLiveData<List<Section>>()
    private val sectionsListLiveData = _sectionsListLiveData

    fun getSectionalsList(): LiveData<List<Section>> {
        return sectionsListLiveData
    }

    suspend fun getTickersSection() {
        mainUseCase.fetchTickersList().collect {
            val list = (it as Response.Success).data
            sectionsList.add(TickersSection(list))
            _sectionsListLiveData.postValue(sectionsList)
        }
    }

    suspend fun getNewsSection() {
        mainUseCase.fetchNewsList().collect {
            val list = (it as Response.Success).data
            sectionsList.add(TopNewsSection(list))
            sectionsList.add(NewsSection(list))
            _sectionsListLiveData.postValue(sectionsList)
        }
    }
}
