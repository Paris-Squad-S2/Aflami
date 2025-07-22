package com.feature.home.homeUi.navigation

import androidx.navigation.NavOptions
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeGraph
import kotlinx.coroutines.flow.Flow

interface HomeNavigator {
    val startGraph: HomeGraph
    val homeNavigationEvent: Flow<HomeNavigationEvent>
    suspend fun navigate(destination: HomeDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}