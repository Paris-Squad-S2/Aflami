package com.paris_2.domain.game.entity

data class MoviePosterQuestion(
    val posterImg: String,
    val options: List<String>,
    val correctAnswer: String
)