package com.repository.media.models.local.tvShow

import kotlinx.serialization.Serializable

@Serializable
data class TVGenreEntity(
    val id: Int,
    val name: String,
)