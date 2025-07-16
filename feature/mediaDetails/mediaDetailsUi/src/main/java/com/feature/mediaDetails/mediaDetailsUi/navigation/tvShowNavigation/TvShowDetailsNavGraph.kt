package com.feature.mediaDetails.mediaDetailsUi.navigation.tvShowNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.feature.mediaDetails.mediaDetailsUi.navigation.NavigationEvent
import com.feature.mediaDetails.mediaDetailsUi.navigation.Navigator
import com.feature.mediaDetails.mediaDetailsUi.navigation.ObserveAsEvents
import com.feature.mediaDetails.mediaDetailsUi.navigation.movieNavigation.buildMovieDetailsGraph
import org.koin.compose.koinInject

@Composable
fun TvShowDetailsNavGraph(navigator: Navigator = koinInject()) {

    val navController = rememberNavController()

    ObserveAsEvents(navigator.navigationEvent) { event ->
        when (event) {
            is NavigationEvent.Navigate -> navController.navigate(
                route = event.destination,
                navOptions = event.navOptions
            )

            NavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController =navController,
        startDestination = navigator.startGraph
    ){
        buildTvShowDetailsGraph()
    }
}