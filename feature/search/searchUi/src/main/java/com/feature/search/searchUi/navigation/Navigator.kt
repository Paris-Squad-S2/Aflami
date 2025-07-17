package com.feature.search.searchUi.navigation

import androidx.navigation.NavOptions
import com.feature.search.searchApi.SearchDestination
import com.feature.search.searchApi.SearchGraph
import kotlinx.coroutines.flow.Flow

interface SearchNavigator {
    val startGraph: SearchGraph
    val searchNavigationEvent: Flow<SearchNavigationEvent>
    suspend fun navigate(destination: SearchDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}