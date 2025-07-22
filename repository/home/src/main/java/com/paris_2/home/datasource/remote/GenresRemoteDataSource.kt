package com.paris_2.home.datasource.remote

import com.paris_2.home.dto.GenresDto

interface GenresRemoteDataSource {
    suspend fun getAllGenres(language: String): GenresDto
}