package com.feature.authentication.authenticationApi

import androidx.compose.runtime.Composable

interface AuthenticationFeatureAPI {
    operator fun invoke(authenticationDestination: AuthenticationDestination? = null) : @Composable () -> Unit
}