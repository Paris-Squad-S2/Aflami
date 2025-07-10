package com.repository.search.mapper

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.repository.search.dto.ResultDto
import com.repository.search.dto.SearchDto
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import kotlinx.datetime.LocalDate


fun List<MediaEntity>.toMedias() = this.map { it.toMedia() }

fun MediaEntity.toMedia(): Media {
    return Media(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toMediaType(),
        categories = this.category,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating
    )
}

fun MediaTypeEntity.toMediaType(): MediaType {
    return when (this) {
        MediaTypeEntity.MOVIE -> MediaType.MOVIE
        MediaTypeEntity.TVSHOW -> MediaType.TVSHOW
    }
}


fun ResultDto.toMediaEntity(
    searchQuery: String,
    actor: List<String>,
    country: String
): MediaEntity? {
    val title = this.title ?: this.name ?: return null
    val image = this.posterPath ?: this.profilePath ?: ""
    val releaseDateStr = this.releaseDate ?: this.firstAirDate ?: return null

    return MediaEntity(
        searchQuery = searchQuery,
        imageUri = image,
        title = title,
        type = when (this.mediaType) {
            "movie" -> MediaTypeEntity.MOVIE
            "tv" -> MediaTypeEntity.TVSHOW
            else -> return null
        },
        category = this.genreIds ?: emptyList(),
        yearOfRelease = LocalDate.parse(releaseDateStr),
        rating = this.voteAverage ?: 0.0,
        country = country,
        actor = actor
    )
}


fun SearchDto.toMediaEntities(
    query: String,
    actor: List<String> = emptyList(),
    country: String = ""
): List<MediaEntity> {
    return results?.mapNotNull {
        it.toMediaEntity(query, actor, country)
    } ?: emptyList()
}
