package com.feature.authentication.authenticationUi.navigation

import androidx.navigation.NavOptions
import com.feature.authentication.authenticationApi.AuthenticationDestination

sealed class AuthenticationNavigationEvent {
    data class Navigate(
        val destination: AuthenticationDestination,
        val navOptions: NavOptions? = null
    ) : AuthenticationNavigationEvent()

    data object NavigateUp : AuthenticationNavigationEvent()
}