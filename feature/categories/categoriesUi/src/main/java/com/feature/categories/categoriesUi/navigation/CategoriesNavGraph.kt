package com.feature.categories.categoriesUi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import com.feature.categories.categoriesUi.screen.CategoriesScreen
import com.feature.categories.categoriesUi.screen.CategoriesScreenViewModel

@Composable
fun CategoriesNavGraph(
    viewModel: CategoriesScreenViewModel = hiltViewModel()
) {
    val navigator = viewModel.navigator
    val navController = rememberNavController()

    ObserveAsEvents(navigator.categoriesNavigationEvent) { event ->
        when (event) {
            is CategoriesNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            CategoriesNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildCategoriesNavGraph()
    }
}

fun NavGraphBuilder.buildCategoriesNavGraph() {
    navigation<CategoriesDestinations.MainGraph>(
        startDestination = CategoriesDestinations.CategoriesScreen
    ) {
        composable<CategoriesDestinations.CategoriesScreen> { CategoriesScreen() }
    }
}
