package com.domain.home.repository

import com.domain.home.model.Media

interface MediaRepository {
    suspend fun getPopularMedia() : List<Media>
    suspend fun getTopRatingMedia() : List<Media>
    suspend fun getUpComingMedia() : List<Media>
    suspend fun getNowPlayingMedia() : List<Media>
}