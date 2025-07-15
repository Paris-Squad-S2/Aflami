package com.datasource.remote.mediadetails.models.movie


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSimilarsDto(
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val movieSimilarDto: List<MovieSimilarDto>? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("total_results") val totalResults: Int? = null
)