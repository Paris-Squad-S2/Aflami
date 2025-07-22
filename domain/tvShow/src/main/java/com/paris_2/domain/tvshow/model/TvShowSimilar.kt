package com.paris_2.domain.tvshow.model

data class TvShowSimilar (
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath : String,
    val releaseDate : String,
)