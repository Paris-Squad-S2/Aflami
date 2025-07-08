package com.feature.search.searchUi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.feature.search.searchUi.screen.filter.FilterScreen
import com.feature.search.searchUi.screen.findByActor.FindByActorScreen
import com.feature.search.searchUi.screen.search.SearchScreen
import com.feature.search.searchUi.screen.worldTour.WorldTourScreen
import org.koin.compose.koinInject

@Composable
fun SearchNavGraph(navigator: Navigator = koinInject()) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.navigationEvent) { event ->
        when (event) {
            is NavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            NavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(navController = navController, startDestination = Destinations.SearchScreen) {
        composable<Destinations.SearchScreen> {
            SearchScreen()
        }
        dialog<Destinations.FilterScreen> {
            FilterScreen()
        }
        composable<Destinations.WorldTourScreen> {
            WorldTourScreen()
        }
        composable<Destinations.FindByActorScreen> {
            FindByActorScreen()
        }
    }
}