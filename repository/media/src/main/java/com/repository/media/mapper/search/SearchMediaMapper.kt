package com.repository.media.mapper.search

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.repository.media.dto.search.KnownForDto
import com.repository.media.dto.search.ResultDto
import com.repository.media.mapper.intListToCategoryList
import kotlinx.datetime.LocalDate

fun KnownForDto.toMedia(): Media? {
    val title = this.title ?: return null
    val releaseDateStr = this.releaseDate ?: return null
    return try {
        Media(
            id = this.id ?: return null,
            imageUri = this.imageUrl ?: "",
            title = title,
            type = when (this.mediaType) {
                "movie" -> MediaType.Movie
                "tv" -> MediaType.TvShow
                else -> return null
            },
            categories = this.genreIds.intListToCategoryList(),
            yearOfRelease = LocalDate.parse(releaseDateStr),
            rating = this.voteAverage
        )
    } catch (_: Exception) {
        null
    }
}

fun ResultDto.toMedia(): Media? {
    val title = this.title ?: this.name ?: return null
    val releaseDateStr = (this.releaseDate?.takeIf { it.isNotBlank() }
        ?: this.firstAirDate?.takeIf { it.isNotBlank() }) ?: return null
    return try {
        Media(
            id = this.id ?: return null,
            imageUri = imageUrl ?: "",
            title = title,
            type = when (this.mediaType) {
                "movie" -> MediaType.Movie
                "tv" -> MediaType.TvShow
                else -> MediaType.Movie
            },
            categories = this.genreIds.intListToCategoryList(),
            yearOfRelease = LocalDate.parse(releaseDateStr),
            rating = this.voteAverage
        )
    } catch (_: Exception) {
        null
    }
}