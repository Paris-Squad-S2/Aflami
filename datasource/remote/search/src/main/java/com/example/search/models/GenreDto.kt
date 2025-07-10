package com.example.search.models

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