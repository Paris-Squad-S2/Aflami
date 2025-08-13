package com.feature.guessGame.guessGameUi.screen.guessbyimage

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class GuessCharacterUIState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val question: List<Question> = emptyList(),
    val isChoiceCorrect: Boolean = false,
    val imageBlur: Dp = 8.dp,
    val score: Int = 0,
)

data class Question(
    val image: String = "",
    val answers: List<String> = emptyList(),
    val isCorrect: Boolean = false,
    val isSelected: Boolean = false,
)