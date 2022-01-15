package com.example.pokemon.di

import com.example.pokemon.utils.BaseSchedulerProvider
import com.example.pokemon.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SchedulerProviderModule {

    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider()
}