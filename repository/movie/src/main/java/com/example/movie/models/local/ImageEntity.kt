package com.example.movie.models.local

import kotlinx.serialization.Serializable

@Serializable
data class ImageEntity(
    val id: Int,
    val url: String,
)