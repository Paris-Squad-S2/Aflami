package com.feature.guessGame.guessGameUi.navigation

import androidx.navigation.NavOptions

sealed class GuessGameNavigationEvent {
    data class Navigate(val destination: Destination, val navOptions: NavOptions? = null) :
        GuessGameNavigationEvent()

    data object NavigateUp : GuessGameNavigationEvent()
}