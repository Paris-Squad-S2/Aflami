package com.feature.guessGame.guessGameUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface GuessGameNavigator {

    val startGraph: GuessGameGraph
    val guessGameNavigationEvent: Flow<GuessGameNavigationEvent>
    suspend fun navigate(destination: GuessGameDestination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}