package com.feature.search.searchUi.screen.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.search.searchUi.navigation.Screen

fun NavGraphBuilder.searchRoute(navController: NavController){
    composable(Screen.SearchScreen.route){
        SearchScreen(navController)
    }
}

fun NavController.navigateToSearchScreen() {
    navigate(Screen.SearchScreen.route)
}