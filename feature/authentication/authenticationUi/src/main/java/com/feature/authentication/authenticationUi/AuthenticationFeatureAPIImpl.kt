package com.feature.authentication.authenticationUi

import androidx.compose.runtime.Composable
import com.feature.authentication.authenticationApi.AuthenticationDestination
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.authentication.authenticationUi.navigation.AuthenticationNavGraph

class AuthenticationFeatureAPIImpl() : AuthenticationFeatureAPI {
    override fun invoke(authenticationDestination: AuthenticationDestination?): @Composable (() -> Unit) = {
        AuthenticationNavGraph(startDestination = authenticationDestination)
    }
}