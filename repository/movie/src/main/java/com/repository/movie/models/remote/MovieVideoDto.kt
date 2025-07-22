package com.repository.movie.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoDto(
    @SerialName("key")
    val key: String? = null,
    @SerialName("results")
    val movieVideoResultDto: List<MovieVideoResultDto>? = null
)