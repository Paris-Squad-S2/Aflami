package com.example.search.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultDto(
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("gender") val gender: Int? = null,
    @SerialName("genre_ids") val genreIds: List<Int>? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("known_for") val knownForDTO: List<KnownForDto>? = null,
    @SerialName("known_for_department") val knownForDepartment: String? = null,
    @SerialName("media_type") val mediaType: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("origin_country") val originCountry: List<String>? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
    @SerialName("original_name") val originalName: String? = null,
    @SerialName("original_title") val originalTitle: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("vote_average") val voteAverage: Double? = null,
    @SerialName("vote_count") val voteCount: Int? = null
)