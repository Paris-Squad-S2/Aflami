package com.repository.movie.models.remote

data class RemoveRatingDto(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)