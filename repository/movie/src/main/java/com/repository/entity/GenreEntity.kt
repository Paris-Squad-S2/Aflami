package com.repository.entity

import kotlinx.serialization.Serializable

@Serializable
data class GenreEntity(
    val id: Int,
    val name: String,
)