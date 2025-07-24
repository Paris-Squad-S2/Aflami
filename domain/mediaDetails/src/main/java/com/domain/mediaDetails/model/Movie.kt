package com.domain.mediaDetails.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val description: String,
    val posterPath: String,
    val genres: List<Genre>,
    val releaseDate: LocalDate,
    val runtime: Int,
    val country: String,
    val productionCompanies: List<ProductionCompany>,
)