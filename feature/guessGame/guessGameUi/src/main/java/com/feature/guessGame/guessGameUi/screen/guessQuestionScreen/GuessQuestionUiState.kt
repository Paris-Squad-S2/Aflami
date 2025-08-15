package com.feature.guessGame.guessGameUi.screen.guessQuestionScreen

import com.feature.guessGame.guessGameUi.R
import com.feature.guessGame.guessGameUi.navigation.QuestionType
import com.feature.guessGame.guessGameUi.screen.guessGameScreen.mapper.UiGameLevel
import com.paris_2.domain.game.entity.Answer
import com.paris_2.domain.game.entity.Question

data class GuessQuestionUiState(
    val gameTitle: String = "",
    val totalQuestions: Int = 0,
    val currentStep: Int = 0,
    val questionUiState: List<UiQuestion> = emptyList(),
    val questionText: String = "",
    val answers: List<UiAnswer> = emptyList(),
    val correctAnswer: String? = null,
    val remainingAnswers: List<String> = emptyList(),
    val selectedAnswer: String? = null,
    val hintUsed: Boolean = false,
    val showNotEnoughPointsDialog: Boolean = false,
    val timePerQuestion: Int = 0,
    val pointsPerQuestion: Int = 0,
    val time: Int = 0,
    val duration: Int = 0,
    val session: GameSessionUi? = GameSessionUi(),
    val error: String? = null,
)

fun QuestionType.getTitleResId(): Int = when (this) {
    QuestionType.GENRE -> R.string.which_genre_title
    QuestionType.RELEASE_YEAR -> R.string.when_was_it_released_title
    QuestionType.ACTOR -> R.string.Guess_the_character
    QuestionType.POSTER -> R.string.guess_the_poster
}

data class GameSessionUi(
    val level: String = UiGameLevel.EASY.name,
    val currentQuestion: String = "",
    val score: Int = 0,
    val isCompleted: Boolean = false,
)


data class UiQuestion(
    val content: String,
    val options: List<UiAnswer>,
    val selectedAnswer: String? = null,
    val hintUsed: Boolean = false,
)

data class UiAnswer(
    val text: String,
    val isCorrect: Boolean,
)

fun Question.toUiQuestion(): UiQuestion {
    return UiQuestion(
        content = content,
        options = options.map { it.toUiAnswer() },
        selectedAnswer = selectedAnswer,
        hintUsed = usedHint
    )
}

fun Answer.toUiAnswer(): UiAnswer = UiAnswer(text = text, isCorrect = isCorrect)

