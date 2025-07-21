package com.domain.home.model

data class ContinueWatchingMedia(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val releaseDate: String,
    val type : MediaType
)
