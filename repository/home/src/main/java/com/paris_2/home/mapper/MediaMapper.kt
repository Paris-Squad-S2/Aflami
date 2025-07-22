package com.paris_2.home.mapper

import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.paris_2.home.dto.MediaDto
import kotlinx.datetime.toLocalDate

fun MediaDto.toMedia(type: MediaType): Media{
    return Media(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        yearOfRelease = this.releaseDate.toLocalDate(),
        genreIds = this.genreIds,
        type = type
    )
}