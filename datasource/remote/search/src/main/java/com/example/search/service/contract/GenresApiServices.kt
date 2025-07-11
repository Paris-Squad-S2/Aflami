package com.example.search.service.contract

import com.repository.search.dto.GenresDto

fun interface GenresApiServices {
    suspend fun getAllGenres(): GenresDto
}