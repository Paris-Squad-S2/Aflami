package com.feature.authentication.authenticationApi

import kotlinx.serialization.Serializable

sealed interface AuthenticationDestinations : AuthenticationGraph {

    @Serializable
    data object AuthenticationGraph1 : AuthenticationGraph

    @Serializable
    data object LoginScreen : AuthenticationDestination

    @Serializable
    data object RegisterWebViewScreen : AuthenticationDestination
}