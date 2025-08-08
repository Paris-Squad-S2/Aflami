package com.paris_2.aflami.bottomNavBar.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface Navigator {
    val startGraph: Graph
    val navigationEvent: Flow<Event>
    suspend fun navigate(destination: Destination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}