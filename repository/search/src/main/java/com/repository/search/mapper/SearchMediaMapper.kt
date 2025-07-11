package com.repository.search.mapper

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.repository.search.dto.KnownForDto
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
): MediaEntity? {
    val title = this.title ?: this.name ?: return null
    val image = this.posterPath ?: this.profilePath ?: ""
    val releaseDateStr = (this.releaseDate?.takeIf { it.isNotBlank() }
        ?: this.firstAirDate?.takeIf { it.isNotBlank() }) ?: return null

    return try {
        MediaEntity(
            searchQuery = searchQuery,
            imageUri = "https://image.tmdb.org/t/p/w500/$image",
            title = title,
            type = when (this.mediaType) {
                "movie" -> MediaTypeEntity.MOVIE
                "tv" -> MediaTypeEntity.TVSHOW
                else -> return null
            },
            category = this.genreIds ?: emptyList(),
            yearOfRelease = LocalDate.parse(releaseDateStr),
            rating = this.voteAverage ?: 0.0
        )
    } catch (_: Exception) {
        null
    }
}

fun KnownForDto.toMediaEntity(
    searchQuery: String
): MediaEntity? {
    val title = this.title ?: return null
    val image = this.posterPath ?: ""
    val releaseDateStr = this.releaseDate ?: return null

    try {
        return MediaEntity(
            searchQuery = searchQuery,
            imageUri = "https://image.tmdb.org/t/p/w500/$image",
            title = title,
            type = when (this.mediaType) {
                "movie" -> MediaTypeEntity.MOVIE
                "tv" -> MediaTypeEntity.TVSHOW
                else -> return null
            },
            category = this.genreIds ?: emptyList(),
            yearOfRelease = LocalDate.parse(releaseDateStr),
            rating = this.voteAverage ?: 0.0,
        )
    } catch (_: Exception) {
        return null
    }
}


fun ResultDto.toMediaEntityForActors(
    searchQuery: String
): List<MediaEntity?> {

    return this.knownForDTO?.map {
        it.toMediaEntity(searchQuery)?.let { mediaEntity ->
            return listOf(mediaEntity)
        }
    } ?: emptyList()
}


fun SearchDto.toMediaEntities(
    query: String
): List<MediaEntity> {
    return results?.mapNotNull {
        it.toMediaEntity(query)
    } ?: emptyList()
}

fun SearchDto.toMediaEntitiesForActors(
    query: String
): List<MediaEntity> {
    return results?.flatMap {
        it.toMediaEntityForActors(query)
    }?.filterNotNull() ?: emptyList()
}