package com.datasource.local.media.datasource

import com.datasource.local.media.dao.GenresUserInteractionDao
import com.repository.media.datasource.local.GenresInteractionDataSource
import com.repository.media.entity.GenreUserInteractionEntity

class GenresInteractionDataSourceImpl(
    private val genresInteractionDao: GenresUserInteractionDao
) : GenresInteractionDataSource {
    override suspend fun upsertGenresInteraction(interaction: GenreUserInteractionEntity) {
        genresInteractionDao.upsertGenresInteraction(interaction)
    }

    override suspend fun getCategoryByGenreId(genreId: Int): Int? {
        return genresInteractionDao.getCategoryByGenreId(genreId)
    }

    override suspend fun getGenresInteractions(): List<GenreUserInteractionEntity> {
        return genresInteractionDao.getGenresInteractions()
    }
}