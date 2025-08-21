package com.paris.aflami.bottomNavBar.bottomNavBarUI.navigation

import androidx.navigation.NavOptions

sealed class Event {
    data class Navigate(val destination: Destination, val navOptions: NavOptions? = null): Event()
    data object NavigateUp: Event()
}