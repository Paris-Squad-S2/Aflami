package com.feature.guessGame.guessGameApi

import androidx.compose.runtime.Composable

interface GuessGameFeatureAPI {
    operator fun invoke() : @Composable () -> Unit
}