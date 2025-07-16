package com.feature.mediaDetails.mediaDetailsUi.ui.navigation

import androidx.navigation.NavOptions
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestination
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsGraph
import kotlinx.coroutines.flow.Flow

interface MediaDetailsNavigator {
    val startGraph: MediaDetailsGraph
    val mediaDetailsNavigationEvent: Flow<MediaDetailsNavigationEvent>
    suspend fun navigate(destination: MediaDetailsDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}