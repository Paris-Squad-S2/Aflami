package com.repository.media.datasource.remote

import com.repository.media.models.remote.media.GenresDto

interface GenresRemoteDataSource {
    suspend fun getMoviesGenres(language: String): GenresDto
}