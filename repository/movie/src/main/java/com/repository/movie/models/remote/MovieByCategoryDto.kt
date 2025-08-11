package com.repository.movie.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieByCategoryDto(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val resultDto: List<ResultDto>,
    @SerialName("total_pages")
    val total_pages: Int,
    @SerialName("total_results")
    val total_results: Int
)