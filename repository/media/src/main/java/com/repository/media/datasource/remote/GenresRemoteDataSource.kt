package com.repository.media.datasource.remote

import com.repository.media.dto.GenresDto

interface GenresRemoteDataSource {
    suspend fun getMoviesGenres(language: String): GenresDto
}