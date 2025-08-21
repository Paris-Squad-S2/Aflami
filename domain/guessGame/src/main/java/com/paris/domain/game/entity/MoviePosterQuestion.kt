package com.paris.domain.game.entity

data class MoviePosterQuestion(
    val posterImg: String,
    val options: List<String>,
    val correctAnswer: String
)