package com.feature.search.searchUi.screen.worldTour

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.feature.search.searchUi.navigation.Screen

fun NavGraphBuilder.worldTourRoute(navController: NavController){
    composable(
        route = "${Screen.WorldTourScreen.route}/{${WorldTourArgs.QUERY}}",
        arguments = listOf(
            navArgument(WorldTourArgs.QUERY) { NavType.StringType }
        )
    ){
        WorldTourScreen(navController)
    }
}

fun NavController.navigateToWorldTour(query: String){
    navigate("${Screen.WorldTourScreen.route}/$query")
}

class WorldTourArgs(savedStateHandle: SavedStateHandle) {
    val query: String = checkNotNull(savedStateHandle[QUERY])

    companion object {
        const val QUERY = "query"
    }
}