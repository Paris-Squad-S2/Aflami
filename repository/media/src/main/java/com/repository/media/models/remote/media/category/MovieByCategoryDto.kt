package com.repository.media.models.remote.media.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieByCategoryDto(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val resultDto: List<ResultDto>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)