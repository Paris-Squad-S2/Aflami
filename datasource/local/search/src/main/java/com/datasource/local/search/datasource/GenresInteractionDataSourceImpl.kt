package com.datasource.local.search.datasource

import com.datasource.local.search.dao.GenresUserInteractionDao
import com.repository.search.dataSource.local.GenresInteractionDataSource
import com.repository.search.entity.GenreUserInteractionEntity

class GenresInteractionDataSourceImpl(
    private val genresInteractionDao: GenresUserInteractionDao
) : GenresInteractionDataSource {
    override suspend fun upsertInteraction(interaction: GenreUserInteractionEntity) {
        genresInteractionDao.upsertInteraction(interaction)
    }

    override suspend fun getCategoryInteractions(genreId: Int): Int? {
        return genresInteractionDao.getCategoryInteractions(genreId)
    }

    override suspend fun getAllInteractions(): List<GenreUserInteractionEntity> {
        return genresInteractionDao.getAllInteractions()
    }
}