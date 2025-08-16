package com.feature.guessGame.guessGameUi

import androidx.compose.runtime.Composable
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.feature.guessGame.guessGameUi.navigation.GuessGameNavGraph
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.GuessGameScreen

class GuessGameFeatureAPIImpl : GuessGameFeatureAPI {
    override fun invoke(): @Composable (() -> Unit) {
        return {
            GuessGameScreen()
        }
    }

}