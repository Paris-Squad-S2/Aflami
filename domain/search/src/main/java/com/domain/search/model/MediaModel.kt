package com.domain.search.model

import kotlinx.datetime.LocalDate

data class Media(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaType,
    val categories: List<Int>,
    val yearOfRelease: LocalDate,
    val rating: Double,
)


enum class MediaType(val displayName: String) {
    TVSHOW("TV Show"),
    MOVIE("Movie")
}