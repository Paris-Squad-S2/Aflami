package com.domain.home.model

data class PopularMedia(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val genres: List<Genre>
)

