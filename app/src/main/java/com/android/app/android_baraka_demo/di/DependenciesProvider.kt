package com.android.app.android_baraka_demo.di

import com.android.app.android_baraka_demo.data.RepositoryImpl
import com.android.app.android_baraka_demo.domain.MainUseCase

object DependenciesProvider {
    private fun provideRepositoryInstance() = RepositoryImpl()
    fun provideMainUseCaseInstance() = MainUseCase(provideRepositoryInstance())
}