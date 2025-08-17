package com.paris_2.domain.game.entity

data class Question(
    val id: String,
    val type: QuestionType,
    val content: String,
    val correctAnswer: String,
    val options: List<Answer>,
    var usedHint: Boolean,
    var selectedAnswer: String? = null
) {
    enum class QuestionType {
        IMAGE,
        TEXT
    }
}