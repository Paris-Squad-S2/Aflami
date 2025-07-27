package com.feature.search.searchUi.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchNavigatorModule {
    @Provides
    @Singleton
    fun provideSearchNavigator(): SearchNavigator {
        return SearchNavigatorImpl(SearchDestinations.SearchGraph1)
    }
}

