package com.paris_2.domain.game.entity


data class GameSession(
    val id: String,
    val level: GameLevel,
    val questions: List<Question>,
    var currentQuestionIndex: Int = 0,
    var score: Int = 0,
    var isCompleted: Boolean = false
) {
    enum class GameLevel { EASY, MEDIUM, HARD }

    fun getCurrentQuestion(): Question? =
        questions.getOrNull(currentQuestionIndex)

    fun moveToNextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
        } else {
            isCompleted = true
        }
    }
}