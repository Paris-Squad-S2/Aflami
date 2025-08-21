package com.repository.media.mapper

import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.entity.Movie
import com.paris.domain.media.entity.TvShow
import com.repository.media.models.remote.media.category.ResultDto
import com.repository.media.models.remote.media.category.TvResultDto
import com.repository.media.models.remote.media.home.MovieDto
import com.repository.media.models.remote.media.home.TvDto
import com.repository.media.models.remote.media.profile.MovieResult
import com.repository.media.models.local.media.Category
import com.repository.media.models.local.media.HomeMediaEntity
import com.repository.media.models.local.media.MediaEntity
import com.repository.media.models.local.media.MediaTypeEntity
import com.repository.media.models.remote.media.profile.TvShowResult
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
        categories = genreIds.intListToCategoryList(),
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
        categories = genreIds.intListToCategoryList(),
        type = type
    )
}

fun MediaTypeEntity.toDomain(): MediaType = when (this) {
    MediaTypeEntity.Movie -> MediaType.Movie
    MediaTypeEntity.TvShow -> MediaType.TvShow
}

fun HomeMediaEntity.toDomain(): Media? {
    val parsedDate = releaseDate.let {
        runCatching { LocalDate.parse(it) }.getOrNull()
    } ?: return null

    return Media(
        id = id,
        imageUri = posterPath,
        title = title,
        rating = voteAverage ?: 0.0,
        yearOfRelease = parsedDate,
        categories = genreIds.intListToCategoryList(),
        type = type.toDomain()
    )
}

fun Media.toMediaEntity(category: Category, language: String): HomeMediaEntity = HomeMediaEntity(
    id = id,
    title = title,
    voteAverage = rating,
    posterPath = imageUri,
    releaseDate = yearOfRelease.toString(),
    genreIds = categories.toIdList(),
    type = type.toEntity(),
    category = category,
    language = language
)

fun Media.toEntity(): MediaEntity = MediaEntity(
    id = id,
    title = title,
    voteAverage = rating,
    posterPath = imageUri,
    releaseDate = yearOfRelease.toString(),
    genreIds = categories.toIdList(),
    type = type.toEntity()
)

fun MediaType.toEntity(): MediaTypeEntity = when (this) {
    MediaType.Movie -> MediaTypeEntity.Movie
    MediaType.TvShow -> MediaTypeEntity.TvShow
}

fun MovieResult.toDomain(type: MediaType): Media? {
    val parsedDate = runCatching { LocalDate.parse(releaseDate ?: "") }.getOrNull() ?: return null
    return Media(
        id = id ?: -1,
        imageUri = posterPath.toImageUrl().orEmpty(),
        title = title.orEmpty(),
        type = type,
        categories = genreIds.intListToCategoryList(),
        yearOfRelease = parsedDate,
        rating = rating ?: 0.0
    )
}

fun TvShowResult.toDomain(type: MediaType): Media? {
    val parsedDate = runCatching { LocalDate.parse(firstAirDate) }.getOrNull() ?: return null
    return Media(
        id = id,
        imageUri = posterPath.toImageUrl().orEmpty(),
        title = name,
        type = type,
        categories = genreIds.intListToCategoryList(),
        yearOfRelease = parsedDate,
        rating = voteAverage
    )
}


fun ResultDto.toDomain(): Media? {
    val parsedDate = runCatching { releaseDate?.let { LocalDate.parse(it) } }.getOrNull() ?: return null
    return Media(
        id = id ?: return null,
        imageUri = posterPath.toImageUrl().orEmpty(),
        title = title.orEmpty(),
        type = MediaType.Movie,
        categories = genreIds.intListToCategoryList(),
        yearOfRelease = parsedDate,
        rating = voteAverage
    )
}

fun TvResultDto.toDomain(): Media? {
    val parsedDate = runCatching { LocalDate.parse(firstAirDate) }.getOrNull() ?: return null
    return Media(
        id = id,
        imageUri = posterPath.toImageUrl().orEmpty(),
        title = name,
        type = MediaType.TvShow,
        categories = genreIds.intListToCategoryList(),
        yearOfRelease = parsedDate,
        rating = voteAverage
    )
}

fun String?.toImageUrl(): String? {
    return this?.let { "https://image.tmdb.org/t/p/w500/$it" }
}

fun TvShow.toMedia(): Media {
    return Media(
        id = this.id,
        title = this.title,
        rating = this.voteAverage,
        imageUri = this.posterPath,
        yearOfRelease = this.releaseDate,
        categories = categories,
        type = MediaType.TvShow,
    )
}

fun Movie.toMedia(): Media {
    return Media(
        id = this.id,
        title = this.title,
        rating = this.voteAverage,
        imageUri = this.posterPath,
        yearOfRelease = this.releaseDate,
        categories = categories,
        type = MediaType.Movie,
    )
}