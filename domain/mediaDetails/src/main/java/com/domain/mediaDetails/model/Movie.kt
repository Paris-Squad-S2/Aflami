package com.domain.mediaDetails.model

data class Movie(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val description: String,
    val posterPath : String,
    val genres : List<Genre>,
    val releaseDate : String,
    val runtime: Int,
    val country: Country,
    val productionCompanies : List<ProductionCompany>
    )