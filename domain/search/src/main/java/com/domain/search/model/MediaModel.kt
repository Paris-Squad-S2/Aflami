package com.domain.search.model

import kotlinx.datetime.LocalDate

data class Media(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaType,
    val category: List<String>,
    val yearOfRelease: LocalDate,
    val rating: Double,
    val country: String,
    val actor: List<String>, //TODO : Need discussion
)


enum class MediaType {
    TVSHOW,
    MOVIE
}