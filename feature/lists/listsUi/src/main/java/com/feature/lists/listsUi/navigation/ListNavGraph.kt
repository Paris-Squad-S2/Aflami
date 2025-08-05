package com.feature.lists.listsUi.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.lists.listsUi.screens.listDetails.ListDetailsScreen
import com.feature.lists.listsUi.screens.listScreen.ListsScreen
import com.feature.lists.listsUi.screens.listScreen.ListsViewModel

@Composable
fun ListNavGraph(
    viewModel: ListsViewModel = hiltViewModel()
) {
    val navigator = viewModel.navigator
    val navController = rememberNavController()

    ObserveAsEvents(navigator.listNavigationEvent) { event ->
        when (event) {
            is ListNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            ListNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildListNavGraph()
    }
}

fun NavGraphBuilder.buildListNavGraph() {
    navigation<ListDestinations.ListGraph1>(
        startDestination = ListDestinations.ListScreen
    ) {
        composable<ListDestinations.ListScreen> { ListsScreen() }
        composable<ListDestinations.ListDetails> { ListDetailsScreen() }
    }
}
