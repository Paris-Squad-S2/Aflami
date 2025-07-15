package com.datasource.remote.mediadetails.models.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowSimilarsDto(
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val tvShowSimilarDto: List<TvShowSimilarDto>? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("total_results") val totalResults: Int? = null
)