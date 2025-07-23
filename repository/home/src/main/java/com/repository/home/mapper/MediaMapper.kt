package com.repository.home.mapper

import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.repository.home.dto.MovieDto
import com.repository.home.dto.TvDto
import com.repository.home.entity.MediaEntity
import com.repository.home.entity.MediaTypeEntity
import kotlinx.datetime.LocalDate

fun MovieDto.toDomain(type: MediaType): Media? {
    val parsedDate = releaseDate?.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    } ?: return null
    return Media(
        id = id ?: -1,
        title = title ?: "[Unknown Title]",
        voteAverage = voteAverage ?: 0.0,
        posterPath = imageUrl.orEmpty(),
        yearOfRelease = parsedDate,
        genreIds = genreIds ?: emptyList(),
        type = type
    )
}

fun TvDto.toDomain(type: MediaType): Media? {
    val parsedDate = firstAirDate?.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    } ?: return null
    return Media(
        id = id ?: -1,
        title = originalName ?: "[Unknown Title]",
        voteAverage = voteAverage ?: 0.0,
        posterPath = imageUrl.orEmpty(),
        yearOfRelease = parsedDate,
        genreIds = genreIds ?: emptyList(),
        type = type
    )
}

fun MediaEntity.toDomain(): Media{
    return Media(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterPath,
        yearOfRelease = LocalDate.parse(this.releaseDate),
        genreIds = this.genreIds,
        type = this.type.toDomain()
    )
}

fun MediaTypeEntity.toDomain(): MediaType {
    return when (this) {
        MediaTypeEntity.MOVIE -> MediaType.MOVIE
        MediaTypeEntity.TV_SHOW -> MediaType.TV_SHOW
    }
}

fun Media.toEntity(): MediaEntity = MediaEntity(
    id = id,
    title = title,
    voteAverage = voteAverage,
    posterPath = posterPath,
    releaseDate = yearOfRelease.toString(),
    genreIds = genreIds,
    type = type.toEntity()
)

fun MediaType.toEntity(): MediaTypeEntity = when (this) {
    MediaType.MOVIE -> MediaTypeEntity.MOVIE
    MediaType.TV_SHOW -> MediaTypeEntity.TV_SHOW
}
