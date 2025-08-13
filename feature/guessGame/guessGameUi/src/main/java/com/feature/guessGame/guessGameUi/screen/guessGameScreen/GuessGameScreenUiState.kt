package com.feature.guessGame.guessGameUi.screen.guessGameScreen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class GuessGameScreenUiState(
    val userPoints: Int = 0,
    val games: List<GameUiState> = listOf(GameUiState()),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

data class GameUiState(
    val isLocked: Boolean = false,
    val pointsToUnlock: Int = 0,
)

data class GameData(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val backgroundColors: List<Color> = emptyList(),
    val trailingImages: List<Painter> = emptyList(),
    val isLocked: Boolean = false,
    val pointsToUnlock: Int = 0,
)
