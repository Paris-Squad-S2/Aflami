package com.repository.media.mapper.search

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.repository.media.dto.search.KnownForDto
import com.repository.media.dto.search.ResultDto
import com.repository.media.dto.search.SearchDto
import com.repository.media.entity.MediaSearchEntity
import com.repository.media.entity.MediaSearchTypeEntity
import com.repository.media.entity.SearchType
import kotlinx.datetime.LocalDate


fun List<MediaSearchEntity>.toMedias() = this.map { it.toMedia() }

fun MediaSearchEntity.toMedia(): Media {
    return Media(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toMediaType(),
        categoryIds = this.category,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating
    )
}

fun MediaSearchTypeEntity.toMediaType(): MediaType {
    return when (this) {
        MediaSearchTypeEntity.MOVIE -> MediaType.MOVIE
        MediaSearchTypeEntity.TVSHOW -> MediaType.TVSHOW
    }
}


fun ResultDto.toMediaEntity(
    searchQuery: String,
    searchType: SearchType,
    page:Int,
    language: String
): MediaSearchEntity? {
    val title = this.title ?: this.name ?: return null
    val releaseDateStr = (this.releaseDate?.takeIf { it.isNotBlank() }
        ?: this.firstAirDate?.takeIf { it.isNotBlank() }) ?: return null

    return try {
        MediaSearchEntity(
            id = this.id ?: return null,
            searchQuery = searchQuery,
            imageUri = imageUrl ?: "",
            title = title,
            type = when (this.mediaType) {
                "movie" -> MediaSearchTypeEntity.MOVIE
                "tv" -> MediaSearchTypeEntity.TVSHOW
                else -> MediaSearchTypeEntity.MOVIE
            },
            category = this.genreIds ?: emptyList(),
            yearOfRelease = LocalDate.parse(releaseDateStr),
            rating = this.voteAverage ?: 0.0,
            searchType = searchType,
            page = page,
            language = language
        )
    } catch (_: Exception) {
        null
    }
}

fun KnownForDto.toMediaEntity(
    searchQuery: String,
    page: Int,
    language: String
): MediaSearchEntity? {
    val title = this.title ?: return null
    val releaseDateStr = this.releaseDate ?: return null

    try {
        return MediaSearchEntity(
            id = this.id ?: return null,
            searchQuery = searchQuery,
            imageUri = imageUrl ?: "",
            title = title,
            type = when (this.mediaType) {
                "movie" -> MediaSearchTypeEntity.MOVIE
                "tv" -> MediaSearchTypeEntity.TVSHOW
                else -> return null
            },
            category = this.genreIds ?: emptyList(),
            yearOfRelease = LocalDate.parse(releaseDateStr),
            rating = this.voteAverage ?: 0.0,
            searchType = SearchType.Actor,
            page = page,
            language = language
        )
    } catch (_: Exception) {
        return null
    }
}


fun ResultDto.toMediaEntityForActors(
    searchQuery: String,
    page:Int,
    language: String
): List<MediaSearchEntity?> {
    return this.knownForDTO?.map {
        it.toMediaEntity(searchQuery,page,language)?.let { mediaEntity ->
            return listOf(mediaEntity)
        }
    } ?: emptyList()
}


fun SearchDto.toMediaEntities(
    query: String,
    searchType: SearchType,
    page: Int,
    language: String
): List<MediaSearchEntity> {
    return results?.mapNotNull {
        it.toMediaEntity(query, searchType , page,language)
    } ?: emptyList()
}

fun SearchDto.toMediaEntitiesForActors(
    query: String,
    page:Int,
    language: String
): List<MediaSearchEntity> {
    return results?.flatMap {
        it.toMediaEntityForActors(query,page,language)
    }?.filterNotNull() ?: emptyList()
}