package com.repository.search.service.contract

import com.repository.search.dto.GenresDto

fun interface GenresApiServices {
    suspend fun getAllGenres(language: String): GenresDto
}