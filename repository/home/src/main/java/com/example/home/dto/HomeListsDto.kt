package com.example.home.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class HomeListsDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<ResultDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("dates")
    val dates: DatesDto? = null
)
