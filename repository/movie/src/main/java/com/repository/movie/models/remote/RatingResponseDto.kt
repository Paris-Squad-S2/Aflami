package com.repository.movie.models.remote

import kotlinx.serialization.Serializable

@Serializable
data class RatingResponseDto(
    val status_code: Int,
    val status_message: String
)