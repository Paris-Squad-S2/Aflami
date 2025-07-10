package com.repository.search.repository

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.MediaLocalDataSource
import com.repository.search.dataSource.remote.SearchRemoteDataSource
import com.repository.search.dto.ResultDto
import com.repository.search.dto.SearchDto
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import com.repository.search.util.getCurrentDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

class SearchMediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaLocalDataSource: MediaLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchHistoryLocalDataSource: HistoryLocalDataSource
) : SearchMediaRepository {

    @OptIn(ExperimentalTime::class)
    override suspend fun getMediaByActor(actorName: String): List<Media> {
        try {
            val media = mediaLocalDataSource.getMediaByActor(actor = actorName)
            if (media.isNotEmpty()) {
                val queryDate =
                    searchHistoryLocalDataSource.getSearchHistoryQuery(actorName)?.searchDate
                val timeZone = TimeZone.Companion.currentSystemDefault()
                if (queryDate != null && queryDate.toInstant(timeZone)
                        .plus(1, DateTimeUnit.HOUR) <= getCurrentDate().toInstant(timeZone)
                ) {
                    return media.toMedias()
                } else {
                    mediaLocalDataSource.clearAllMediaBySearchQuery(actorName)
                }
            }
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchPerson(query = actorName)
                val mediaEntities = searchDto.toMediaEntities(
                    query = actorName,
                    actor = listOf(actorName)
                )
                mediaLocalDataSource.addAllMedia(mediaEntities)
                searchHistoryLocalDataSource.addSearchQuery(actorName)
            } else {
                throw Exception()
            }
            return mediaLocalDataSource.getMediaByActor(actor = actorName).toMedias()

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMoviesByCountry(countryName: String): List<Media> {
        return try {
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchCountryCode(query = countryName)
                val mediaEntities = searchDto.toMediaEntities(
                    query = countryName,
                    country = countryName
                )
                mediaLocalDataSource.addAllMedia(mediaEntities)
                mediaEntities.toMedias()
            } else {
                mediaLocalDataSource.getMediaByCountry(country = countryName).toMedias()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMediaByQuery(query: String): List<Media> {
        return try {
            if (networkConnectionChecker.isConnected.value) {
                val searchDto = searchRemoteDataSource.searchMulti(query)
                val mediaEntities = searchDto.toMediaEntities(
                    query = query
                )
                mediaLocalDataSource.addAllMedia(mediaEntities)
                mediaEntities.toMedias()
            } else {
                mediaLocalDataSource.getMediaByTitleQuery(query = query).toMedias()
            }
        } catch (e: Exception) {
            throw (e)
        }
    }
}


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
        category = this.genreIds?.map { it.toString() } ?: emptyList(),
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
