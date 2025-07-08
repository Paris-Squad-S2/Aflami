package com.domain.search.repository

import com.domain.search.model.Media

interface TvShowRepository {
    suspend fun getTvShowsByActor(actorName: String): List<Media>
    suspend fun getAllTvShows(): List<Media>
}