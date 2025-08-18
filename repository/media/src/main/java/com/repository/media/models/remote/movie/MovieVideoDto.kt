package com.repository.media.models.remote.movie


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoDto(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("results")
    val movieVideoResultDto: List<MovieVideoResultDto>? = null
)