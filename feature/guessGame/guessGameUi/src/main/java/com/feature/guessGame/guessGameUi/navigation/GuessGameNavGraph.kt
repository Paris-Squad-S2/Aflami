package com.feature.guessGame.guessGameUi.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.feature.guessGame.guessGameUi.screen.GuessGameScreen.GuessGameScreen
import com.feature.guessGame.guessGameUi.screen.GuessGameScreen.GuessGameScreenViewModel


@Composable
fun GuessGameNavGraph(
    guessGameScreenViewModel: GuessGameScreenViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    val navigator = guessGameScreenViewModel.navigator

    ObserveAsEvents(navigator.guessGameNavigationEvent) { event ->
        when (event) {
            is GuessGameNavigationEvent.Navigate -> navController.navigate(
                route = event.destination, navOptions = event.navOptions
            )

            GuessGameNavigationEvent.NavigateUp -> navController.navigateUp()
        }
    }

    NavHost(
        navController = navController,
        startDestination = navigator.startGraph
    ) {
        buildGuessGameNavGraph()
    }

}

fun NavGraphBuilder.buildGuessGameNavGraph() {
    navigation<GuessGameDestinations.GuessGameGraph1>(
        startDestination = GuessGameDestinations.GuessGameScreen
    ) {
        composable<GuessGameDestinations.GuessGameScreen> { GuessGameScreen() }

    }
}