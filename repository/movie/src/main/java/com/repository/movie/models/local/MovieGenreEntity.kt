package com.repository.movie.models.local

import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreEntity(
    val id: Int,
    val name: String,
)