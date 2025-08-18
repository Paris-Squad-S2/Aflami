package com.repository.media.models.local.moive

import kotlinx.serialization.Serializable

@Serializable
data class MovieImageEntity(
    val id: Int,
    val url: String,
)