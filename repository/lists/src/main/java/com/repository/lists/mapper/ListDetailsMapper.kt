package com.repository.lists.mapper

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Media
import com.repository.lists.model.dto.ListDetailsDto
import com.repository.lists.model.dto.MediaDetailsDto

fun ListDetailsDto.toDomain(): ListDetails {
    return ListDetails(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        items = this.mediaDetailsDto?.map { it.toDomain() } ?: emptyList()
    )
}

fun MediaDetailsDto.toDomain(): Media {
    return Media(
        posterPath = this.posterPath.orEmpty(),
        title = this.title.orEmpty(),
        voteAverage = this.voteAverage ?: 0.0,
        releaseDate = this.releaseDate.orEmpty()
    )
}