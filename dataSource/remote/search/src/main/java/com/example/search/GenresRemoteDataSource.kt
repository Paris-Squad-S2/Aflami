package com.example.search

import com.example.search.models.GenreDto

interface GenresRemoteDataSource {
    suspend fun getAllGenres(): GenreDto
}