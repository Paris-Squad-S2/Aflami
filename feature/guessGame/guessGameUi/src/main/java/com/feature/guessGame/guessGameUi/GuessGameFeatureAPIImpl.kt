package com.feature.guessGame.guessGameUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.feature.guessGame.guessGameApi.GuessGameFeatureAPI
import com.paris_2.aflami.designsystem.components.AppText

class GuessGameFeatureAPIImpl : GuessGameFeatureAPI {
    override fun invoke(): @Composable (() -> Unit) {
        return {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppText(
                    text = "Let's play Feature",
                )
            }
        }
    }

}