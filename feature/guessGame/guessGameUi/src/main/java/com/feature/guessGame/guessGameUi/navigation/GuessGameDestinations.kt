package com.feature.guessGame.guessGameUi.navigation

import kotlinx.serialization.Serializable

sealed interface GuessGameDestinations : GuessGameGraph {

    @Serializable
    data object GuessGameGraph1 : GuessGameGraph

    @Serializable
    data object GuessGameScreen : GuessGameDestination

    @Serializable
    data object GuessCharacterScreen : GuessGameDestination

    @Serializable
    data object GuessMovieScreen : GuessGameDestination

    @Serializable
    data object GuessReleasedYearScreen : GuessGameDestination

    @Serializable
    data object GuessGenreScreen : GuessGameDestination

    @Serializable
    data object FinishGameScreen : GuessGameDestination

}