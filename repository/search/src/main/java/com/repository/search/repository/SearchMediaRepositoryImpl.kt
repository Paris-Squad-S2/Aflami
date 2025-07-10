package com.repository.search.repository

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository
import com.example.search.SearchRemoteDataSource
import com.example.search.models.ResultDto
import com.example.search.models.SearchDto
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.MediaLocalDataSource
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import kotlinx.datetime.toLocalDate

class SearchMediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaLocalDataSource: MediaLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchMediaRepository {

    override suspend fun getMediaByActor(actorName: String): List<Media> {
        return try {
            if (networkConnectionChecker.isConnected.value){
                val searchDto = searchRemoteDataSource.searchPerson(query = actorName)
                val mediaEntities = searchDto.toMediaEntities(
                    query = actorName,
                    actor = listOf(actorName)
                )
                mediaLocalDataSource.addAllMedia(mediaEntities)
                mediaEntities.toMedias()
            }
            else{
                mediaLocalDataSource.getMediaByActor(actor = actorName).toMedias()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMoviesByCountry(countryName: String): List<Media> {
        return try {
            if (networkConnectionChecker.isConnected.value){
                val searchDto = searchRemoteDataSource.searchCountryCode(query = countryName)
                val mediaEntities = searchDto.toMediaEntities(
                    query = countryName,
                    country = countryName
                )
                mediaLocalDataSource.addAllMedia(mediaEntities)
                mediaEntities.toMedias()
            }
            else
            {
                mediaLocalDataSource.getMediaByCountry(country = countryName).toMedias()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMediaByQuery(query: String): List<Media> {
        return try {
            if (networkConnectionChecker.isConnected.value){
                val searchDto = searchRemoteDataSource.searchMulti(query)
                val mediaEntities = searchDto.toMediaEntities(
                    query = query
                )
                mediaLocalDataSource.addAllMedia(mediaEntities)
                mediaEntities.toMedias()
            }
            else{
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
        yearOfRelease = releaseDateStr.toLocalDate(),
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
