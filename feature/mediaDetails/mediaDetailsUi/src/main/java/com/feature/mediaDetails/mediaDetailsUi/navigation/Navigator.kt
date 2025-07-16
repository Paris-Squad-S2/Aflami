package com.feature.mediaDetails.mediaDetailsUi.navigation

import androidx.navigation.NavOptions
import com.feature.mediaDetails.mediaDetailsUi.navigation.NavigationEvent
import kotlinx.coroutines.flow.Flow

interface Navigator {

    val startGraph: Graph

    val navigationEvent: Flow<NavigationEvent>

    suspend fun navigate(destination: Destination, navOptions: NavOptions? = null)

    suspend fun navigateUp()
}