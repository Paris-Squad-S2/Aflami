package com.feature.guessGame.guessGameUi.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.GuessGameScreen
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.GuessGameScreenViewModel
import com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.GuessQuestionScreen
import com.feature.guessGame.guessGameUi.screen.guessbyimage.GuessByImageScreen
import com.feature.guessGame.guessGameUi.screen.resultScreen.ResultScreen


@Composable
fun GuessGameNavGraph(
    guessGameScreenViewModel: GuessGameScreenViewModel = hiltViewModel(),
    destination: Destination
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
        buildGuessGameNavGraph(destination)
    }

}

fun NavGraphBuilder.buildGuessGameNavGraph(destination: Destination) {
    navigation<Destinations.Graph1>(
        startDestination = destination
    ) {
        composable<Destinations.Screen> { GuessGameScreen() }

        composable<Destinations.GuessQuestionScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Destinations.GuessQuestionScreen>()
            GuessQuestionScreen(questionType = args.questionType)
        }
        composable<Destinations.GuessByImageScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Destinations.GuessByImageScreen>()
            GuessByImageScreen()
        }
        composable<Destinations.FinishGameScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<Destinations.FinishGameScreen>()
            ResultScreen(
                totalGameTime = args.totalGameTime,
                totalGamePoints = args.totalGamePoints,
                gameType = args.gameType,
            )
        }

    }
}