package com.paris_2.domain.media.entity

import kotlinx.datetime.LocalDate

data class Media(
    val id: Int,
    val imageUri: String,
    val title: String,
    val type: MediaType,
    val genres: List<Genre>,
    val yearOfRelease: LocalDate,
    val rating: Double?,
)


enum class MediaType {
    TVSHOW,
    MOVIE
}