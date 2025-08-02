package com.paris_2.domain.media.entity

data class TvShowSimilar (
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val posterPath : String,
    val releaseDate : String,
)