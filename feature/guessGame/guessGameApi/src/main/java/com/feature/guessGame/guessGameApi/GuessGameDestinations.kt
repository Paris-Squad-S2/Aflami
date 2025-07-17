package com.feature.guessGame.guessGameApi

import kotlinx.serialization.Serializable

sealed interface GuessGameDestinations : GuessGameGraph {

    @Serializable
    data object GuessGameGraph1 : GuessGameGraph

    @Serializable
    data object GuessGameScreen : GuessGameDestination
}