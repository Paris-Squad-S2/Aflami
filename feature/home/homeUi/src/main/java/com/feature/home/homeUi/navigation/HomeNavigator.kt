package com.feature.home.homeUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface HomeNavigator {
    val startGraph: HomeGraph
    val homeNavigationEvent: Flow<HomeNavigationEvent>
    suspend fun navigate(destination: HomeDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}