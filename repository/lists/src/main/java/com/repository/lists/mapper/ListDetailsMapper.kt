package com.repository.lists.mapper

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Media
import com.repository.lists.model.dto.ListDetailsDto
import com.repository.lists.model.dto.MediaDetailsDto
import kotlinx.datetime.LocalDate

fun ListDetailsDto.toDomain(): ListDetails {
    return ListDetails(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        items = this.mediaDetailsDto?.map { it.toDomain() } ?: emptyList()
    )
}

fun MediaDetailsDto.toDomain(): Media {
    return Media(
        id = this.id ?: 0,
        posterPath = this.posterPath.orEmpty(),
        title = this.title.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        releaseDate = try {
            LocalDate.parse(this.releaseDate.orEmpty().substring(0,10))
        } catch (_: Exception) {
            LocalDate(9999, 1, 1)
        }
    )
}