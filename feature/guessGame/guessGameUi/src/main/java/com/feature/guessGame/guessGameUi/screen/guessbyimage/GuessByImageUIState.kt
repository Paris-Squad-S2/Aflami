package com.feature.guessGame.guessGameUi.screen.guessbyimage

data class GuessCharacterUIState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val question: List<Question> = emptyList(),
    val isChoiceCorrect: Boolean = false,
    val screenTitle: String = "",
    val score: Int = 0,
    val currentQuestion: Int = 0,
)

data class Question(
    val image: String = "",
    val answers: List<String> = emptyList(),
    val isCorrect: Boolean = false,
    val isSelected: Boolean = false,
)

enum class CardState {
    Hard,
    Medium,
    Show,
}