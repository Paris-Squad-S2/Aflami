package com.repository.search.dataSource.remote

import com.repository.search.dto.GenreDto

interface GenresRemoteDataSource {
    suspend fun getAllGenres(): GenreDto
}