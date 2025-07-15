package com.paris_2.aflami.appnavigation

import androidx.navigation.NavOptions

sealed class NavigationEvent {
    data class Navigate(val destination: Destination, val navOptions: NavOptions? = null): NavigationEvent()
    data object NavigateUp: NavigationEvent()
}