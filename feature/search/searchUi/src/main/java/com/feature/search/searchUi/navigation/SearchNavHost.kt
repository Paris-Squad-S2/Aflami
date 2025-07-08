package com.feature.search.searchUi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.feature.search.searchUi.screen.filter.FilterScreen
import com.feature.search.searchUi.screen.findByActor.FindByActorScreen
import com.feature.search.searchUi.screen.search.SearchScreen
import com.feature.search.searchUi.screen.worldTour.WorldTourScreen

@Composable
fun SearchNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destinations.SearchScreen) {
        composable<Destinations.SearchScreen> {
            SearchScreen()
        }
        composable<Destinations.FilterScreen> {
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