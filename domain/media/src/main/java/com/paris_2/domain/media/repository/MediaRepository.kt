package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Media

interface MediaRepository {
    suspend fun getPopularMedia() : List<Media>
    suspend fun getTopRatingMedia() : List<Media>
    suspend fun getUpComingMedia() : List<Media>
    suspend fun getNowPlayingMedia() : List<Media>
    suspend fun addMediaToContinueWatching(media: Media)
    suspend fun getMediaFromLocal(): List<Media>
}