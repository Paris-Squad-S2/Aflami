package com.repository.model.local

import kotlinx.serialization.Serializable

@Serializable
data class TVImageEntity(
    val id: Int,
    val url: String,
)