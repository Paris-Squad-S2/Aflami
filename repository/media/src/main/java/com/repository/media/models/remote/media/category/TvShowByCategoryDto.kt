package com.repository.media.models.remote.media.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowByCategoryDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val tvResultDto: List<TvResultDto>,
    @SerialName("total_pages")
    val total_pages: Int,
    @SerialName("total_results")
    val total_results: Int
)