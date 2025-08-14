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
    val showNotEnoughPointsDialog: Boolean = false,
    val timePerQuestion: Int = 0,
    val pointsPerQuestion: Int = 0,
    val userPoints: Int = 0,
)

fun QuestionType.getTitleResId(): Int = when (this) {
    QuestionType.GENRE -> R.string.which_genre_title
    QuestionType.RELEASE_YEAR -> R.string.when_was_it_released_title
    QuestionType.BY_IMAGE -> R.string.Guess_the_character
}

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String,
)

fun getFakeGuessQuestionUiState(): GuessQuestionUiState {
    val sampleQuestions = listOf(
        Question(
            text = "In which year was 'Batman' released?",
            answers = listOf("2008", "2010", "2012", "2014"),
            correctAnswer = "2010"
        ),
        Question(
            text = "Which genre does 'Inception' belong to?",
            answers = listOf("Action", "Sci-Fi", "Comedy", "Drama"),
            correctAnswer = "Sci-Fi"
        ),
        Question(
            text = "Who directed 'Inception'?",
            answers = listOf(
                "Steven Spielberg",
                "Christopher Nolan",
                "James Cameron",
                "Martin Scorsese"
            ),
            correctAnswer = "Christopher Nolan"
        )
    )

    val firstQuestion = sampleQuestions.first()

    return GuessQuestionUiState(
        gameTitle = "guess_release_year",
        totalQuestions = sampleQuestions.size,
        currentStep = 0,
        questionText = firstQuestion.text,
        answers = firstQuestion.answers,
        correctAnswer = firstQuestion.correctAnswer,
        remainingAnswers = firstQuestion.answers,
        selectedAnswer = null,
        hintUsed = false,
        timePerQuestion = 30,
        pointsPerQuestion = 10,
        userPoints = 50
    )
}
