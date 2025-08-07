package com.repository.lists.mapper

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Media
import com.repository.lists.model.dto.ListDetailsDto
import com.repository.lists.model.dto.MediaDetailsDto
import kotlinx.datetime.LocalDate

fun ListDetailsDto.toDomain(): ListDetails {
    val mappedItems = this.items?.map { it.toDomain() } ?: emptyList()
    return ListDetails(
        id = this.id?:0,
        name = this.name.orEmpty(),
        items = mappedItems
    )
}

fun MediaDetailsDto.toDomain(): Media {
    return Media(
        id = this.id ?: 0,
        imageUrl = this.posterPath.toImageUrl().orEmpty(),
        title = this.title.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        releaseDate = try {
            LocalDate.parse(this.releaseDate.orEmpty().substring(0,10))
        } catch (_: Exception) {
            LocalDate(9999, 1, 1)
        }
    )
}

fun String?.toImageUrl(): String? {
    return this?.let { "https://image.tmdb.org/t/p/w500/$it" }
}