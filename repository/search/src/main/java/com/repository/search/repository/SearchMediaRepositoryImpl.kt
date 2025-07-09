package com.repository.search.repository

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository
import com.repository.search.dataSource.MediaLocalDataSource
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.MediaTypeEntity

class SearchMediaRepositoryImpl(
    private val isNetworkConnected: Boolean,
    private val mediaLocalDataSource: MediaLocalDataSource,
//    private val mediaRemoteDateSource: MediaRemoteDataSource
) : SearchMediaRepository {
    override suspend fun getMediaByActor(actorName: String): List<Media> {
       return if (isNetworkConnected)
            TODO()
        else
         mediaLocalDataSource.getMediaByActor(actor = actorName).toMedias()
    }

    override suspend fun getMoviesByCountry(countryName: String): List<Media> {
        return if (isNetworkConnected)
            TODO()
        else mediaLocalDataSource.getMediaByCountry(country = countryName).toMedias()
    }

    override suspend fun getMediaByQuery(query: String): List<Media> {
        return if (isNetworkConnected)
            TODO()
        else mediaLocalDataSource.getMediaByTitleQuery(query = query).toMedias()
    }
}


fun List<MediaEntity>.toMedias() = this.map { it.toMedia() }

fun MediaEntity.toMedia(): Media{
    return Media(
        id = this.id,
        imageUri =this.imageUri,
        title = this.title,
        type = this.type.toMediaType(),
        categories = this.category,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating
    )
}

fun MediaTypeEntity.toMediaType(): MediaType{
    return when(this){
        MediaTypeEntity.MOVIE -> MediaType.MOVIE
        MediaTypeEntity.TVSHOW -> MediaType.TVSHOW
    }
}