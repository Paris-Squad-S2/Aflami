package com.repository.movie.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewsDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val results: List<MovieReviewDto>? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("total_results") val totalResults: Int? = null
)

