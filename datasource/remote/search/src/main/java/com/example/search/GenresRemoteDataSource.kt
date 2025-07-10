package com.example.search

import com.example.search.models.GenresDto

fun interface GenresRemoteDataSource {
    suspend fun getAllGenres(): GenresDto
}