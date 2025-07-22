package com.paris_2.domain.movie.model

data class Movie(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val description: String,
    val posterPath: String,
    val movieGenres: List<MovieGenre>,
    val releaseDate: String,
    val runtime: Int,
    val country: String,
    val productionCompanies: List<MovieProductionCompany>,
)