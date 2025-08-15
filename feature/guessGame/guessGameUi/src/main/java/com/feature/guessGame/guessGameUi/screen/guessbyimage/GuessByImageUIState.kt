package com.feature.guessGame.guessGameUi.screen.guessbyimage

data class GuessCharacterUIState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val questionUiState: List<QuestionUiState> = emptyList(),
    val isChoiceCorrect: Boolean = false,
    val screenTitle: String = "",
    val score: Int = 0,
    val duration: Int = 0,
    val currentQuestion: Int = 0,
)

data class QuestionUiState(
    val image: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswer: String = "",
    val selectedAnswer: String? = null,
)

fun com.paris_2.domain.game.entity.Question.toUiModel(): com.feature.guessGame.guessGameUi.screen.guessbyimage.QuestionUiState {
    return com.feature.guessGame.guessGameUi.screen.guessbyimage.QuestionUiState(
        image = content,
        answers = options.map { it.text },
        correctAnswer = correctAnswer,
        selectedAnswer = selectedAnswer
    )
}

enum class CardState {
    Hard,
    Medium,
    Show,
}