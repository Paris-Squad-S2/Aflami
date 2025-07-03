package com.domain.game.models

data class Question(
    val id: String,
    val type: QuestionType,
    val content: String,
    val correctAnswer: String,
    val options: List<Answer>,
    val usedHint: Boolean
) {
    enum class QuestionType {
        IMAGE,
        TEXT
    }
}