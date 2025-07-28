package com.feature.search.searchUi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.search.searchUi.screen.findByActor.FindByActorScreen
import com.feature.search.searchUi.screen.search.SearchScreen
import com.feature.search.searchUi.screen.worldTour.WorldTourScreen
import dagger.hilt.android.EntryPointAccessors

@Composable
fun SearchNavGraph(
    navigator: SearchNavigator = EntryPointAccessors.fromApplication(
        LocalContext.current.applicationContext as android.app.Application,
        SearchNavigatorEntryPoint::class.java
    ).searchNavigator(),
    startDestination: SearchDestination? = null
) {
    val navController = rememberNavController()

    ObserveAsEvents(navigator.searchNavigationEvent) { event ->
        when (event) {
            is SearchNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            SearchNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildSearchNavGraph(startDestination)
    }
}

fun NavGraphBuilder.buildSearchNavGraph(startDestination: SearchDestination? = null) {
    navigation<SearchDestinations.SearchGraph1>(startDestination = startDestination ?: SearchDestinations.SearchScreen) {
        composable<SearchDestinations.SearchScreen> { SearchScreen() }
        composable<SearchDestinations.WorldTourScreen> { WorldTourScreen() }
        composable<SearchDestinations.FindByActorScreen> { FindByActorScreen() }
    }
}
