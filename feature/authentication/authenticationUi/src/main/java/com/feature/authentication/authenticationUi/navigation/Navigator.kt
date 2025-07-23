package com.feature.authentication.authenticationUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface AuthenticationNavigator {
    val startGraph: AuthenticationGraph
    val authenticationNavigationEvent: Flow<AuthenticationNavigationEvent>
    suspend fun navigate(destination: AuthenticationDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}