package com.repository.media.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatedTvShowDtoo(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<TvShowResult>,
    @SerialName("total_pages")
    val total_pages: Int,
    @SerialName("total_results")
    val total_results: Int
)