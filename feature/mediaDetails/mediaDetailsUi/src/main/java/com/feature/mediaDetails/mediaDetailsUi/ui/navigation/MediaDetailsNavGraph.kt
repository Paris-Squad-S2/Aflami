package com.feature.mediaDetails.mediaDetailsUi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestination
import com.feature.mediaDetails.mediaDetailsApi.MediaDetailsDestinations
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.MovieDetailsScreen
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.tvShow.TvShowDetailsScreen
import org.koin.compose.koinInject

@Composable
fun MediaDetailsNavGraph(
    navigator: MediaDetailsNavigator = koinInject(),
    mediaDetailsDestination: MediaDetailsDestination? = null
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
        buildSearchNavGraph(mediaDetailsDestination)
    }
}

fun NavGraphBuilder.buildSearchNavGraph(startDestination: MediaDetailsDestination? = null) {
    navigation<MediaDetailsDestinations.MediaDetailsGraph1>(startDestination = startDestination ?: MediaDetailsDestinations.MovieDetailsScreen) {
        composable<MediaDetailsDestinations.MovieDetailsScreen> { MovieDetailsScreen() }
        composable<MediaDetailsDestinations.TvShowDetailsScreen> { TvShowDetailsScreen() }
    }
}
