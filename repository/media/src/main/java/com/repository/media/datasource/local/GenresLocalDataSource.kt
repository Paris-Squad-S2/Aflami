package com.repository.media.datasource.local

import com.repository.media.entity.GenreEntity

interface GenresLocalDataSource {
    suspend fun addGenres(genres: List<GenreEntity>)
    suspend fun getGenres(language: String): List<GenreEntity>
}