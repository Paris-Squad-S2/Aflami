package com.datasource.local.datasource

import com.datasource.local.dao.GenresDao
import com.repository.datasorce.local.GenresLocalDataSource
import com.repository.entity.GenreEntity


class GenresLocalDataSourceImpl(
    private val dao: GenresDao,
) : GenresLocalDataSource {
    override suspend fun addGenres(genres: List<GenreEntity>) = dao.addGenres(genres)

    override suspend fun getGenres(): List<GenreEntity> = dao.getGenres()
}