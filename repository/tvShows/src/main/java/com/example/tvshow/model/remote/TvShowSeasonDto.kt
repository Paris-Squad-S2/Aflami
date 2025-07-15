package com.example.tvshow.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowSeasonDto(
    @SerialName("air_date") val airDate: String? = null,
    @SerialName("episodes") val episodesDto: List<TvShowEpisodeDto>? = null,
    @SerialName("_id") val _id: String? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("season_number") val seasonNumber: Int? = null,
    @SerialName("vote_average") val voteAverage: Double? = null
)