package com.paris_2.domain.game.entity

data class Answer(
    val questionId: String,
    val selectedAnswer: String,
    val isCorrect: Boolean
)