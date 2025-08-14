package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen.mapper

import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.paris_2.domain.game.entity.GameSession

fun UiGameLevel.toUiLevel(): GameSession.GameLevel = when (this) {
    UiGameLevel.EASY -> GameSession.GameLevel.EASY
    UiGameLevel.MEDIUM -> GameSession.GameLevel.MEDIUM
    UiGameLevel.HARD -> GameSession.GameLevel.HARD
}
