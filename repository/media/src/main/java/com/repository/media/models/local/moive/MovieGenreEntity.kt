package com.repository.media.models.local.moive

import kotlinx.serialization.Serializable

@Serializable
data class MovieGenreEntity(
    val id: Int,
    val name: String,
)