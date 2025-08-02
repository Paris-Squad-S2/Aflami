package com.domain.mediaDetails.entity

data class TvShowSimilar (
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val posterPath : String,
    val releaseDate : String,
)