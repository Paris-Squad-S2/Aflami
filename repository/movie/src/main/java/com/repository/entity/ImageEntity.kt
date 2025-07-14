package com.repository.entity

import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class ImageEntity(
    @PrimaryKey
    val id: Int,
    val url: String,
)