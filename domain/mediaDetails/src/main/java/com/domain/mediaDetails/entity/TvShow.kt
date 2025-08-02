package com.domain.mediaDetails.entity

import kotlinx.datetime.LocalDate

data class TvShow (
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val description: String,
    val posterPath : String,
    val genres : List<Genre>,
    val releaseDate : LocalDate,
    val runtime: Int,
    val seasons: List<Season>,
    val country: String,
    val productionCompanies : List<ProductionCompany>
)