package com.repository.entity

import kotlinx.serialization.Serializable

@Serializable
data class ImageEntity(
    val id: Int,
    val url: String,
)