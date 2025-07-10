package com.example.search.api

import com.repository.search.models.GenreDto

interface GenresApiServices {
    suspend fun getAllGenres(): com.repository.search.models.GenreDto
}