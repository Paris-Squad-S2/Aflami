package com.feature.home.homeUi.navigation

import androidx.navigation.NavOptions
import com.feature.home.homeApi.HomeDestination

sealed class HomeNavigationEvent {
    data class Navigate(val destination: HomeDestination, val navOptions: NavOptions? = null) :
        HomeNavigationEvent()

    data object NavigateUp : HomeNavigationEvent()
}