package com.paris_2.aflami.appnavigation

import androidx.navigation.NavOptions

sealed class AppNavigationEvent {
    data class Navigate(val destination: AppDestination, val navOptions: NavOptions? = null): AppNavigationEvent()
    data object NavigateUp: AppNavigationEvent()
}