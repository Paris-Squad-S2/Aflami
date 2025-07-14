package com.feature.search.searchUi.screen.findByActor

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.feature.search.searchUi.navigation.Screen
import com.feature.search.searchUi.screen.findByActor.FindByActorArgs.Companion.QUERY

fun NavGraphBuilder.findByActorRoute(navController: NavController){
    composable(
        route = "${Screen.FindByActorScreen.route}/{${QUERY}}",
        arguments = listOf(
            navArgument(QUERY) { NavType.StringType }
        )
    ){
        FindByActorScreen(navController)
    }
}

fun NavController.navigateToFindByActor(query: String){
    navigate("${Screen.FindByActorScreen.route}/$query")
}

class FindByActorArgs(savedStateHandle: SavedStateHandle) {
    val query: String = checkNotNull(savedStateHandle[QUERY])

    companion object {
        const val QUERY = "query"
    }
}