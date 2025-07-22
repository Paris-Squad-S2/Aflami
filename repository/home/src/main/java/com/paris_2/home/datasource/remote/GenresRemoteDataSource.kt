package com.paris_2.home.datasource.remote

import com.repository.home.dto.GenresDto

interface GenresRemoteDataSource {
    suspend fun getAllGenres(language: String): GenresDto
}