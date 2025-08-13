package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import com.feature.guessGame.guessGameUi.R
import com.feature.guessGame.guessGameUi.navigation.QuestionType

data class GuessQuestionUiState(
    val gameTitle: String = "",
    val totalQuestions: Int = 0,
    val currentStep: Int = 0,
    val questionText: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswer: String? = null,
    val remainingAnswers: List<String> = emptyList(),
    val selectedAnswer: String? = null,
    val hintUsed: Boolean = false,
    val timePerQuestion: Int = 30,
    val pointsPerQuestion: Int = 0
)


fun getGameTitleResId(questionType: QuestionType): Int {
    return when (questionType) {
        QuestionType.GENRE -> R.string.which_genre_title
        QuestionType.RELEASE_YEAR -> R.string.when_was_it_released_title
    }
}

