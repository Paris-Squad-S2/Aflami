package com.repository.media.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatedMovieDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<MovieResult>,
    @SerialName("total_pages")
    val total_pages: Int,
    @SerialName("total_results")
    val total_results: Int
)
