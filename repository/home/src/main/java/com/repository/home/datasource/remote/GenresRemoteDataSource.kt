package com.repository.home.datasource.remote

import com.repository.home.dto.GenresDto

interface GenresRemoteDataSource {
    suspend fun getMoviesGenres(language: String): GenresDto
}