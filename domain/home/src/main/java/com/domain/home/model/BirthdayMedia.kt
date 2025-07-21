package com.domain.home.model

data class BirthdayMedia(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val releaseDate: String,
    val type : MediaType
)
