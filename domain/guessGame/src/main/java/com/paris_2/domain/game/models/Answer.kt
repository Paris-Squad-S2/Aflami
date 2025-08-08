package com.paris_2.domain.game.models

data class Answer(
    val questionId: String,
    val selectedAnswer: String,
    val isCorrect: Boolean
)