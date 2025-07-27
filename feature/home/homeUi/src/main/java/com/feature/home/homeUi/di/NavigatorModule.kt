package com.feature.home.homeUi.di

import com.feature.home.homeUi.navigation.HomeNavigator
import com.feature.home.homeUi.navigation.HomeNavigatorImpl
import com.feature.home.homeApi.HomeDestinations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigatorModule {
    @Provides
    @Singleton
    fun provideHomeNavigator(): HomeNavigator {
        return HomeNavigatorImpl(startGraph = HomeDestinations.HomeGraph1)
    }
}

