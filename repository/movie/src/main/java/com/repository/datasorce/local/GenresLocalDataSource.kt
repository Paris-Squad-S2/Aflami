package com.repository.datasorce.local

import com.repository.entity.GenreEntity

interface GenresLocalDataSource {
    suspend fun addGenres(genres: List<GenreEntity>)
    suspend fun getGenres(): List<GenreEntity>
}