package com.repository.movie.models.local

import kotlinx.serialization.Serializable

@Serializable
data class MovieImageEntity(
    val id: Int,
    val url: String,
)