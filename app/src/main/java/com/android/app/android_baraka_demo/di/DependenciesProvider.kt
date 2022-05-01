package com.android.app.android_baraka_demo.di

import android.content.Context
import com.android.app.android_baraka_demo.data.RepositoryImpl
import com.android.app.android_baraka_demo.data.network.RetrofitClient
import com.android.app.android_baraka_demo.domain.MainUseCase

object DependenciesProvider {
    private fun provideApiService() = RetrofitClient.getInstance().service

    private fun provideRepositoryInstance(applicationContext: Context) =
        RepositoryImpl(applicationContext, provideApiService())

    fun provideMainUseCaseInstance(applicationContext: Context) =
        MainUseCase(provideRepositoryInstance(applicationContext))
}