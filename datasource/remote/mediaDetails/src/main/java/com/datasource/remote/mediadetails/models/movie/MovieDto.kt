package com.datasource.remote.mediadetails.models.movie

import com.datasource.remote.mediadetails.models.tvShow.TvShowBelongsToCollectionDto
import com.datasource.remote.mediadetails.models.tvShow.TvShowGenreDto
import com.datasource.remote.mediadetails.models.tvShow.TvShowProductionCompanyDto
import com.datasource.remote.mediadetails.models.tvShow.TvShowProductionCountryDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("belongs_to_collection") val tvShowBelongsToCollectionDto: TvShowBelongsToCollectionDto? = null,
    @SerialName("budget") val budget: Int? = null,
    @SerialName("genres") val tvShowGenreDto: List<TvShowGenreDto>? = null,
    @SerialName("homepage") val homepage: String? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("imdb_id") val imdbId: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("production_companies") val productionCompanies: List<TvShowProductionCompanyDto>? = null,
    @SerialName("production_countries") val productionCountries: List<TvShowProductionCountryDto>? = null,
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