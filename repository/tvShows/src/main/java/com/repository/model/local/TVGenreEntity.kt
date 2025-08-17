package com.repository.model.local

import kotlinx.serialization.Serializable

@Serializable
data class TVGenreEntity(
    val id: Int,
    val name: String,
)