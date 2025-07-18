package com.feature.guessGame.guessGameUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.guessGame.guessGameApi.GuessGameDestination
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI

class GuessGameFeatureAPIImpl : GuessGameFeatureAPI {
    override fun invoke(guessGameDestination: GuessGameDestination?): @Composable (() -> Unit) {
        return {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Let's play Feature",
                )
            }
        }
    }

}