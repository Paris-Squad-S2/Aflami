package com.domain.mediaDetails.model

data class MovieSimilar (
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath : String,
    val releaseDate : String,
)