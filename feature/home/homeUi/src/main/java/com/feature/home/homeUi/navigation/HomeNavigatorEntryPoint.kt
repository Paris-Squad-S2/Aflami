package com.feature.home.homeUi.navigation

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HomeNavigatorEntryPoint {
    fun homeNavigator(): HomeNavigator
}