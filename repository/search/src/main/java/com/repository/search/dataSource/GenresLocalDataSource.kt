package com.repository.search.dataSource

import com.repository.search.entity.GenreEntity

interface GenresLocalDataSource {
    suspend fun addGenres(genres: List<GenreEntity>)
    suspend fun getGenres(): List<GenreEntity>
}