package com.feature.guessGame.guessGameUi.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow

interface GuessGameNavigator {

    val startGraph: Graph
    val guessGameNavigationEvent: Flow<GuessGameNavigationEvent>
    suspend fun navigate(destination: Destination, navOptions: NavOptions? = null)
    suspend fun navigateUp()
}