package com.paris_2.aflami.appnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(navigator: Navigator = koinInject()) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.navigationEvent) { event ->
        when (event) {
            is NavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            NavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildSearchNavGraph()
    }
}

fun NavGraphBuilder.buildSearchNavGraph() {
    navigation<AppDestinations.AppGraph>(startDestination = AppDestinations.SearchFeature) {
//        composable<AppDestinations.SearchFeature> { SearchScreen() }
//        composable<AppDestinations.DetailsFeature> { WorldTourScreen() }
    }
}
