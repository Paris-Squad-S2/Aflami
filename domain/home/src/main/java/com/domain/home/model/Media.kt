package com.domain.home.model

import kotlinx.datetime.LocalDate

data class Media(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val yearOfRelease: LocalDate,
    val genres: List<Genre>,
    val type : MediaType
)
