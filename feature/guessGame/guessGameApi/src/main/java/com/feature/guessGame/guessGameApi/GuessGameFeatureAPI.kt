package com.feature.guessGame.guessGameApi

import androidx.compose.runtime.Composable

interface GuessGameFeatureAPI {
    operator fun invoke(guessGameDestination: GuessGameDestination? = null) : @Composable () -> Unit
}