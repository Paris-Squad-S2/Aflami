package com.datasource.local.search.datasource

import com.datasource.local.search.dao.GenresDao
import com.repository.search.dataSource.local.GenresLocalDataSource
import com.repository.search.entity.GenreEntity

class GenresLocalDataSourceImpl(
    private val dao: GenresDao
) : GenresLocalDataSource {
    override suspend fun addGenres(genres: List<GenreEntity>) = dao.addGenres(genres)
    override suspend fun getGenres(language: String): List<GenreEntity> = dao.getGenres(language)
}