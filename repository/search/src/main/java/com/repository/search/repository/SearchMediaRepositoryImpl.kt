package com.repository.search.repository

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository
import com.example.search.SearchRemoteDataSource
import com.repository.search.NetworkConnectionChecker
import com.repository.search.dataSource.MediaLocalDataSource
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity
import com.repository.search.mapper.toMediaList

class SearchMediaRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val mediaLocalDataSource: MediaLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchMediaRepository {

    override suspend fun getMediaByActor(actorName: String): List<Media> {
        return try {
            if (networkConnectionChecker.isConnected.value)
                searchRemoteDataSource.searchPerson(query = actorName).toMediaList()
            else
                mediaLocalDataSource.getMediaByActor(actor = actorName).toMedias()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMoviesByCountry(countryName: String): List<Media> {
        return try {
            if (networkConnectionChecker.isConnected.value)
                searchRemoteDataSource.searchCountryCode(query = countryName).toMediaList()
            else mediaLocalDataSource.getMediaByCountry(country = countryName).toMedias()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMediaByQuery(query: String): List<Media> {
        return try {
            if (networkConnectionChecker.isConnected.value)
                searchRemoteDataSource.searchMulti(query).toMediaList()
            else mediaLocalDataSource.getMediaByTitleQuery(query = query).toMedias()
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