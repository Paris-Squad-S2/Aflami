package com.paris_2.home

import com.paris_2.home.dto.GenresDto

interface GenresApiServices {
    suspend fun getAllGenres(language: String): GenresDto
}