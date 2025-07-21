package com.feature.authentication.authenticationUi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.authentication.authenticationApi.AuthenticationDestination
import com.feature.authentication.authenticationApi.AuthenticationDestinations
import com.feature.authentication.authenticationUi.screen.login.LoginScreen
import org.koin.compose.koinInject

@Composable
fun AuthenticationNavGraph(
    navigator: AuthenticationNavigator = koinInject(),
    startDestination: AuthenticationDestination? = null
) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.authenticationNavigationEvent) { event ->
        when (event) {
            is AuthenticationNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            AuthenticationNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildSearchNavGraph(startDestination)
    }
}

fun NavGraphBuilder.buildSearchNavGraph(startDestination: AuthenticationDestination? = null) {
    navigation<AuthenticationDestinations.AuthenticationGraph1>(startDestination = startDestination ?: AuthenticationDestinations.LoginScreen) {
        composable<AuthenticationDestinations.LoginScreen> { LoginScreen() }
    }
}
