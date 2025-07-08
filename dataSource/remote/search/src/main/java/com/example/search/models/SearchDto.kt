package com.example.search.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val results: List<ResultDto>? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("total_results") val totalResults: Int? = null
)