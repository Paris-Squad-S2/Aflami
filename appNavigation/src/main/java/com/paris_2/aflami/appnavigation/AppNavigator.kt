package com.paris_2.aflami.appnavigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface AppNavigator {
    val startGraph: AppGraph
    val navigationEvent: Flow<AppNavigationEvent>
    suspend fun navigate(destination: AppDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}