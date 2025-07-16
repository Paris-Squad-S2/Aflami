package com.repository.search.dataSource.local

import com.repository.search.entity.GenreEntity

interface GenresLocalDataSource {
    suspend fun addGenres(genres: List<GenreEntity>)
    suspend fun getGenres(language: String): List<GenreEntity>
}