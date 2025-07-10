package com.repository.search.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val genres: List<Genre>,
)

@Serializable
data class Genre(
    val id: Int,
    val name: String,
)