package com.paris_2.home.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MediaListDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<MediaDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
    @SerialName("dates")
    val dates: DatesDto? = null
)
