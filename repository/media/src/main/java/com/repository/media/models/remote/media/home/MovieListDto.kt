package com.repository.media.models.remote.media.home

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MovieListDto(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<MovieDto>? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("total_results")
    val totalResults: Int? = null,

    @SerialName("dates")
    val dates: DatesDto? = null
)
