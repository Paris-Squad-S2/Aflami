package com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper

enum class UiGameLevel {
    EASY, MEDIUM, HARD
}


fun Int.toUiGameLevel(): UiGameLevel = when (this) {
    0 -> UiGameLevel.EASY
    1 -> UiGameLevel.MEDIUM
    2 -> UiGameLevel.HARD
    else -> UiGameLevel.EASY
}
