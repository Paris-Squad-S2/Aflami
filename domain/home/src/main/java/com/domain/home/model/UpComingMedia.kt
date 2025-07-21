package com.domain.home.model

import kotlinx.datetime.LocalDate

data class UpComingMedia(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val yearOfRelease: LocalDate,
    val type : MediaType,
    val genres: List<Genre>
)
