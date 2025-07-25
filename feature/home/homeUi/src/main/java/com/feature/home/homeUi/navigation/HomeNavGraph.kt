package com.feature.home.homeUi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.home.homeApi.HomeDestination
import com.feature.home.homeApi.HomeDestinations
import com.feature.home.homeUi.screen.continueWatching.ContinueWatchingScreen
import com.feature.home.homeUi.screen.home.HomeScreen
import com.feature.home.homeUi.screen.topRatingMovies.TopRatingMoviesScreen
import org.koin.compose.koinInject

@Composable
fun HomeNavGraph(
    navigator: HomeNavigator = koinInject(),
    startDestination: HomeDestination? = null
) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.homeNavigationEvent) { event ->
        when (event) {
            is HomeNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            HomeNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildSearchNavGraph(startDestination)
    }
}

fun NavGraphBuilder.buildSearchNavGraph(startDestination: HomeDestination? = null) {
    navigation<HomeDestinations.HomeGraph1>(
        startDestination = startDestination ?: HomeDestinations.HomeScreen
    ) {
        composable<HomeDestinations.HomeScreen> { HomeScreen() }
        composable<HomeDestinations.ContinueWatchingScreen> { ContinueWatchingScreen() }
        composable<HomeDestinations.TopRatingMoviesScreen> { TopRatingMoviesScreen() }
    }
}
