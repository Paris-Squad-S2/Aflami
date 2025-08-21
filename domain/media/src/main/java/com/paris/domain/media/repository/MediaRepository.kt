package com.paris.domain.media.repository

import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.Media
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun getPopularMedia() : List<Media>
    suspend fun getTopRatingMedia() : List<Media>
    suspend fun getUpComingMedia() : List<Media>
    suspend fun getNowPlayingMedia() : List<Media>
    suspend fun addMediaToContinueWatching(media: Media)
    fun getContinueWatchingMedia(): Flow<List<Media>>
    suspend fun getRatedMedia(accountId: Int): List<Media>
    suspend fun getMoviesByCategory(category: Category, page: Int): List<Media>
    suspend fun getTvShowsByCategory(category: Category, page: Int): List<Media>
}