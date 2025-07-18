package com.repository.movie.models.local

import kotlinx.serialization.Serializable

@Serializable
data class GenreEntity(
    val id: Int,
    val name: String,
)