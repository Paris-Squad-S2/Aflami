package com.repository.home.mapper

import com.domain.media.entity.Media
import com.domain.media.entity.MediaType
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
        rating = voteAverage ?: 0.0,
        imageUri = imageUrl.orEmpty(),
        yearOfRelease = parsedDate,
        categoryIds = genreIds ?: emptyList(),
        type = type
    )
}

fun TvDto.toDomain(type: MediaType): Media? {
    val parsedDate = firstAirDate?.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    } ?: return null
    return Media(
        id = id ?: -1,
        title = name ?: "[Unknown Title]",
        rating = voteAverage ?: 0.0,
        imageUri = imageUrl.orEmpty(),
        yearOfRelease = parsedDate,
        categoryIds = genreIds ?: emptyList(),
        type = type
    )
}

fun MediaEntity.toDomain(): Media{
    return Media(
        id = this.id,
        title = this.title,
        rating = this.voteAverage,
        imageUri = this.posterPath,
        yearOfRelease = LocalDate.parse(this.releaseDate),
        categoryIds = this.genreIds,
        type = this.type.toDomain()
    )
}

fun MediaTypeEntity.toDomain(): MediaType {
    return when (this) {
        MediaTypeEntity.MOVIE -> MediaType.MOVIE
        MediaTypeEntity.TV_SHOW -> MediaType.TVSHOW
    }
}

fun Media.toEntity(): MediaEntity = MediaEntity(
    id = id,
    title = title,
    voteAverage = rating,
    posterPath = imageUri,
    releaseDate = yearOfRelease.toString(),
    genreIds = categoryIds,
    type = type.toEntity()
)

fun MediaType.toEntity(): MediaTypeEntity = when (this) {
    MediaType.MOVIE -> MediaTypeEntity.MOVIE
    MediaType.TVSHOW -> MediaTypeEntity.TV_SHOW
}
