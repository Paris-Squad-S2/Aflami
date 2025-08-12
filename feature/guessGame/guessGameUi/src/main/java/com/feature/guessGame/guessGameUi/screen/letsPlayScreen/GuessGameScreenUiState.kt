package com.feature.guessGame.guessGameUi.screen.letsPlayScreen

data class LetsPlayScreenUiState(
    val userPoints: Int = 0,
    val games: List<GameUiState> = listOf(GameUiState()),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class GameUiState(
    val isLocked: Boolean = false,
    val pointsToUnlock: Int = 0
)