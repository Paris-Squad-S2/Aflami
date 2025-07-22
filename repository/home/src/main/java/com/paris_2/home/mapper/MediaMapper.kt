package com.paris_2.home.mapper

import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.paris_2.home.dto.MediaDto
import com.paris_2.home.dto.MediaListDto
import kotlinx.datetime.LocalDate
fun MediaDto.toDomain(type: MediaType): Media {
    return Media(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        yearOfRelease = LocalDate.parse(this.releaseDate),
        genreIds = this.genreIds,
        type = type
    )
}
fun MediaListDto.toDomainList(type: MediaType): List<Media> {
    return results.map { it.toDomain(type) }
}
