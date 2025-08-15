package com.feature.guessGame.guessGameUi.screen.guessbyimage

import androidx.annotation.StringRes

data class GuessCharacterUIState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val questionUiState: List<QuestionUiState> = emptyList(),
    val isChoiceCorrect: Boolean = false,
    @StringRes
    val screenTitle: Int? = null,
    val score: Int = 0,
    val duration: Int = 0,
    val time: Int = 0,
    val currentQuestion: Int = 0,
    val showNotEnoughPointsDialog: Boolean = false,
    val hintUsed: Boolean = false,
)

data class QuestionUiState(
    val image: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswer: String = "",
    val selectedAnswer: String? = null,
    val usedHint: Boolean = false,
)

fun com.paris_2.domain.game.entity.Question.toUiModel(): QuestionUiState {
    return QuestionUiState(
        image = content,
        answers = options.map { it.text },
        correctAnswer = correctAnswer,
        selectedAnswer = selectedAnswer,
        usedHint = usedHint
    )
}