package com.feature.authentication.authenticationUi.navigation

import androidx.navigation.NavOptions
import com.feature.authentication.authenticationApi.AuthenticationDestination
import com.feature.authentication.authenticationApi.AuthenticationGraph
import kotlinx.coroutines.flow.Flow

interface AuthenticationNavigator {
    val startGraph: AuthenticationGraph
    val authenticationNavigationEvent: Flow<AuthenticationNavigationEvent>
    suspend fun navigate(destination: AuthenticationDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}