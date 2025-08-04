package com.repository.media.dto.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvListDto(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<TvDto>? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("total_results")
    val totalResults: Int? = null
)
