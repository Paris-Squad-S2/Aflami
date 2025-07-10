package com.example.search.service.contract

import com.example.search.models.GenresDto

fun interface GenresApiServices {
    suspend fun getAllGenres(): GenresDto
}