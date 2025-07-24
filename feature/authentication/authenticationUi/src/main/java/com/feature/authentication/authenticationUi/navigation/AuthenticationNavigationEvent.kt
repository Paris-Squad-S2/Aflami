package com.feature.authentication.authenticationUi.navigation

import androidx.navigation.NavOptions

sealed class AuthenticationNavigationEvent {
    data class Navigate(
        val destination: AuthenticationDestination,
        val navOptions: NavOptions? = null
    ) : AuthenticationNavigationEvent()

    data object NavigateUp : AuthenticationNavigationEvent()
}