package com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper

import com.feature.guessGame.guessGameUi.R

enum class UiGameLevel {
    EASY, MEDIUM, HARD
}


fun Int.toUiGameLevel(): UiGameLevel = when (this) {
    R.string.Easy -> UiGameLevel.EASY
    R.string.Medium -> UiGameLevel.MEDIUM
    R.string.Hard -> UiGameLevel.HARD
    else -> UiGameLevel.EASY
}