package com.paris_2.domain.media.entity

import kotlinx.datetime.LocalDate

data class TvShow (
    val id: Int,
    val title: String,
    val voteAverage: Double?,
    val description: String,
    val posterPath : String,
    val categories : List<Category>,
    val releaseDate : LocalDate,
    val runtime: Int,
    val seasons: List<Season>,
    val country: String,
    val productionCompanies : List<ProductionCompany>
)