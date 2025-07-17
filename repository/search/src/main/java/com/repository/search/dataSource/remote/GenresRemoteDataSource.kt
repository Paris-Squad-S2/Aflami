package com.repository.search.dataSource.remote

import com.repository.search.dto.GenresDto

fun interface GenresRemoteDataSource {
    suspend fun getAllGenres(language: String): GenresDto
}