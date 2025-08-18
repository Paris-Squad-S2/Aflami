package com.repository.media.models.local.tvShow

import kotlinx.serialization.Serializable

@Serializable
data class TVImageEntity(
    val id: Int,
    val url: String,
)