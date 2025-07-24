package com.repository.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowVideoDto(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("results")
    val tvShowVideoResultDto: List<TvShowVideoResultDto>? = null
)