package com.repository.movie.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("belongs_to_collection") val movieBelongsToCollectionDto: MovieBelongsToCollectionDto? = null,
    @SerialName("budget") val budget: Int? = null,
    @SerialName("genres") val movieGenreDto: List<MovieGenreDto>? = null,
    @SerialName("homepage") val homepage: String? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("production_companies") val productionCompanies: List<MovieProductionCompanyDto>? = null,
    @SerialName("production_countries") val productionCountries: List<MovieProductionCountryDto>? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("revenue") val revenue: Int? = null,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("tagline") val tagline: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null
)