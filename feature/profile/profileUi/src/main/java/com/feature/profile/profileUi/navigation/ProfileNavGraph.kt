package com.feature.profile.profileUi.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.profile.profileUi.screen.ProfileScreen
import com.feature.profile.profileUi.screen.ProfileViewModel
import com.feature.profile.profileUi.screen.myRating.MyRatingScreen
import com.feature.profile.profileUi.screen.watchHistory.WatchHistoryScreen


@Composable
fun ProfileNavGraph(
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    val navigator = profileViewModel.navigator

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
    navigation<ProfileDestinations.ProfileMainGraph>(
        startDestination = ProfileDestinations.ProfileScreen
    ) {
        composable<ProfileDestinations.ProfileScreen> { ProfileScreen() }
        composable<ProfileDestinations.WatchHistoryScreen> { WatchHistoryScreen() }
        composable<ProfileDestinations.MyRatingScreen> { MyRatingScreen() }
    }
}