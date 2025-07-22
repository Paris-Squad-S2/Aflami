package com.paris_2.domain.tvshow.model

data class TvShow (
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val description: String,
    val posterPath : String,
    val tvShowGenres : List<TvShowGenre>,
    val releaseDate : String,
    val runtime: Int,
    val seasons: List<Season>,
    val country: String,
    val productionCompanies : List<TvShowProductionCompany>
)