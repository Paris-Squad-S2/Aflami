package com.feature.mediaDetails.mediaDetailsUi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.feature.authentication.authenticationApi.AuthenticationFeatureAPI
import com.feature.mediaDetails.mediaDetailsUi.ui.comon.components.LoginDialog
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.cast.MovieCastScreen
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.MovieDetailsScreen
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.cast.TvShowCastScreen
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.details.TvShowDetailsScreen

@Composable
fun MediaDetailsNavGraph(
    navigator: MediaDetailsNavigator,
    authenticationFeatureAPI: AuthenticationFeatureAPI,
    mediaDetailsDestination: MediaDetailsDestination? = null,
) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.mediaDetailsNavigationEvent) { event ->
        when (event) {
            is MediaDetailsNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            MediaDetailsNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {

        buildDetailsNavGraph(
            startDestination = mediaDetailsDestination,
            navController = navController,
            authenticationFeatureAPI = authenticationFeatureAPI
        )
    }
}

fun NavGraphBuilder.buildDetailsNavGraph(
    startDestination: MediaDetailsDestination? = null,
    navController: NavController,
    authenticationFeatureAPI: AuthenticationFeatureAPI,
) {
    navigation<MediaDetailsDestinations.MediaDetailsGraph1>(
        startDestination = startDestination ?: MediaDetailsDestinations.MovieDetailsScreen,
    ) {
        composable<MediaDetailsDestinations.MovieDetailsScreen> { MovieDetailsScreen() }
        composable<MediaDetailsDestinations.TvShowDetailsScreen> { TvShowDetailsScreen() }
        composable<MediaDetailsDestinations.MovieCastScreen> { MovieCastScreen() }
        composable<MediaDetailsDestinations.TvShowCastScreen> { TvShowCastScreen() }
        dialog<MediaDetailsDestinations.LoginDialogDestination> { backStackEntry ->
            val destination =
                backStackEntry.toRoute<MediaDetailsDestinations.LoginDialogDestination>()
            LoginDialog(
                title = destination.title,
                onDismiss = { navController.navigateUp() },
                onLoginClick = {
                    authenticationFeatureAPI()
                }
            )
        }
    }
}
