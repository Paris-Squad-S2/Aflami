package com.feature.search.searchUi.navigation

import androidx.navigation.NavOptions
import com.feature.search.searchApi.SearchDestination

sealed class SearchNavigationEvent {
    data class Navigate(val destination: SearchDestination, val navOptions: NavOptions? = null): SearchNavigationEvent()
    data object NavigateUp: SearchNavigationEvent()
}