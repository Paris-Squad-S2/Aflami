package com.repository.lists.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListsDto(
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val listDto: List<ListDto>? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("total_results") val totalResults: Int? = null
)