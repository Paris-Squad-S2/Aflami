package com.feature.profile.profileUi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.profile.profileUi.screen.ProfileScreen
import com.feature.profile.profileUi.screen.changepassword.ChangePasswordWebViewScreen
import dagger.hilt.android.EntryPointAccessors

@Composable
fun ProfileNavGraph(
    navigator: ProfileNavigator = EntryPointAccessors.fromApplication(
        LocalContext.current.applicationContext as android.app.Application,
        ProfileNavigatorEntryPoint::class.java
    ).profileNavigator(),
    startDestination: ProfileDestination? = null,
) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.profileNavigationEvent) { event ->
        when (event) {
            is ProfileNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            ProfileNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildSearchNavGraph(startDestination)
    }
}

fun NavGraphBuilder.buildSearchNavGraph(startDestination: ProfileDestination? = null) {
    navigation<ProfileDestinations.ProfileMainGraph>(
        startDestination = startDestination ?: ProfileDestinations.ProfileScreen
    ) {
        composable<ProfileDestinations.ProfileScreen> { ProfileScreen() }
        composable<ProfileDestinations.WebView> { ChangePasswordWebViewScreen() }
    }
}
