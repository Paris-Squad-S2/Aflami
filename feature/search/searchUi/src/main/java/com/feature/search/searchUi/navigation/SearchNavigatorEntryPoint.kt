package com.feature.search.searchUi.navigation

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SearchNavigatorEntryPoint {
    fun searchNavigator(): SearchNavigator
}
