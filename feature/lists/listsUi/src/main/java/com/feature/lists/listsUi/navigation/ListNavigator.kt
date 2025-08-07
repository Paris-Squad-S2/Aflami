package com.feature.lists.listsUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface ListNavigator {
    val startGraph: ListGraph
    val listNavigationEvent: Flow<ListNavigationEvent>
    suspend fun navigate(destination: ListDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}