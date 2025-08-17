package com.feature.guessGame.guessGameUi.screen.resultScreen

data class ResultUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val totalSessionPoints: Int = 0,
    val totalSessionDuration: Int = 0,
    val gameType: String = "",
)