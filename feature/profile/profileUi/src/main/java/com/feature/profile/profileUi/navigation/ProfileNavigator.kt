package com.feature.profile.profileUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface ProfileNavigator {
    val startGraph: ProfileGraph
    val profileNavigationEvent: Flow<ProfileNavigationEvent>
    suspend fun navigate(destination: ProfileDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}