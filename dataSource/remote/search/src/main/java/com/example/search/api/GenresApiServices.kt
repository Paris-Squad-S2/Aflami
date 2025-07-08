package com.example.search.api

import com.example.search.models.GenreDto

interface GenresApiServices {
    suspend fun getAllGenres(): GenreDto
}