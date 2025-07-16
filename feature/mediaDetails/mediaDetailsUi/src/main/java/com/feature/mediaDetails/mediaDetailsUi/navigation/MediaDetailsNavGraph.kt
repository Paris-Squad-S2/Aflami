package com.feature.mediaDetails.mediaDetailsUi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject

@Composable
fun MediaDetailsNavGraph(navigator: Navigator = koinInject()) {

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
        buildMediaDetailsGraph()
    }
}