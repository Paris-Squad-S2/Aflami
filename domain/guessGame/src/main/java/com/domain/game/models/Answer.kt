package com.domain.game.models

data class Answer(
    val questionId: String,
    val selectedAnswer: String,
    val isCorrect: Boolean
)