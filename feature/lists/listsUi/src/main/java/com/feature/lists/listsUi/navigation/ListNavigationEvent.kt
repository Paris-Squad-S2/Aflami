package com.feature.lists.listsUi.navigation

import androidx.navigation.NavOptions

sealed class ListNavigationEvent {
    data class Navigate(val destination: ListDestination, val navOptions: NavOptions? = null) :
        ListNavigationEvent()

    data object NavigateUp : ListNavigationEvent()
}