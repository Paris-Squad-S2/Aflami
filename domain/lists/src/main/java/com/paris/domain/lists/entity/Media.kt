package com.paris.domain.lists.entity

import kotlinx.datetime.LocalDate

data class Media(
    val id: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val releaseDate: LocalDate
)