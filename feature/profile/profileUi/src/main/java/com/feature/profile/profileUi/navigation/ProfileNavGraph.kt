package com.feature.profile.profileUi.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.profile.profileUi.screen.myRating.MyRatingScreen
import com.feature.profile.profileUi.screen.watchHistory.WatchHistoryScreen
import dagger.hilt.android.EntryPointAccessors


@Composable
fun ProfileNavGraph(
    navigator: ProfileNavigator = EntryPointAccessors.fromApplication(
        LocalContext.current.applicationContext as Application,
        ProfileNavigatorEntryPoint ::class.java
    ).profileNavigator()
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
        buildProfileNavGraph()
    }
}

fun NavGraphBuilder.buildProfileNavGraph() {
    navigation<ProfileDestinations.ProfileGraph1>(
        startDestination = ProfileDestinations.MyRatingScreen
    ) {
        composable<ProfileDestinations.WatchHistoryScreen> { WatchHistoryScreen() }
        composable<ProfileDestinations.MyRatingScreen> { MyRatingScreen() }
    }
}