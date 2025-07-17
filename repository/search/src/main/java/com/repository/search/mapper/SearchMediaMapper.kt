package com.repository.search.mapper

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.repository.search.dto.KnownForDto
import com.repository.search.dto.ResultDto
import com.repository.search.dto.SearchDto
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import com.repository.search.entity.SearchType
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
    searchType: SearchType,
    page:Int
): MediaEntity? {
    val title = this.title ?: this.name ?: return null
    val releaseDateStr = (this.releaseDate?.takeIf { it.isNotBlank() }
        ?: this.firstAirDate?.takeIf { it.isNotBlank() }) ?: return null

    return try {
        MediaEntity(
            id = this.id ?: return null,
            searchQuery = searchQuery,
            imageUri = imageUrl ?: "",
            title = title,
            type = when (this.mediaType) {
                "movie" -> MediaTypeEntity.MOVIE
                "tv" -> MediaTypeEntity.TVSHOW
                else -> MediaTypeEntity.MOVIE
            },
            category = this.genreIds ?: emptyList(),
            yearOfRelease = LocalDate.parse(releaseDateStr),
            rating = this.voteAverage ?: 0.0,
            searchType = searchType,
            page = page
        )
    } catch (_: Exception) {
        null
    }
}

fun KnownForDto.toMediaEntity(
    searchQuery: String,
    page: Int
): MediaEntity? {
    val title = this.title ?: return null
    val releaseDateStr = this.releaseDate ?: return null

    try {
        return MediaEntity(
            id = this.id ?: return null,
            searchQuery = searchQuery,
            imageUri = imageUrl ?: "",
            title = title,
            type = when (this.mediaType) {
                "movie" -> MediaTypeEntity.MOVIE
                "tv" -> MediaTypeEntity.TVSHOW
                else -> return null
            },
            category = this.genreIds ?: emptyList(),
            yearOfRelease = LocalDate.parse(releaseDateStr),
            rating = this.voteAverage ?: 0.0,
            searchType = SearchType.Actor,
            page = page
        )
    } catch (_: Exception) {
        return null
    }
}


fun ResultDto.toMediaEntityForActors(
    searchQuery: String,
    page:Int
): List<MediaEntity?> {
    return this.knownForDTO?.map {
        it.toMediaEntity(searchQuery,page)?.let { mediaEntity ->
            return listOf(mediaEntity)
        }
    } ?: emptyList()
}


fun SearchDto.toMediaEntities(
    query: String,
    searchType: SearchType,
    page: Int
): List<MediaEntity> {
    return results?.mapNotNull {
        it.toMediaEntity(query, searchType,page)
    } ?: emptyList()
}

fun SearchDto.toMediaEntitiesForActors(
    query: String,
    page:Int
): List<MediaEntity> {
    return results?.flatMap {
        it.toMediaEntityForActors(query,page)
    }?.filterNotNull() ?: emptyList()
}