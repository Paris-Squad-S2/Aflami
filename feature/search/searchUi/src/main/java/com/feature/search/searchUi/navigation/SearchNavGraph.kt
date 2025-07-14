package com.feature.search.searchUi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.feature.search.searchUi.screen.findByActor.findByActorRoute
import com.feature.search.searchUi.screen.search.searchRoute
import com.feature.search.searchUi.screen.worldTour.worldTourRoute

@Composable
fun SearchNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SearchScreen.route
    ) {
        searchRoute(navController)
        worldTourRoute(navController)
        findByActorRoute(navController)
    }
}