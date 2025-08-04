package com.repository.media.repository

import com.paris_2.domain.media.entity.GenreUserInteraction
import com.paris_2.domain.media.repository.GenresInteractionRepository
import com.repository.media.datasource.local.GenresInteractionDataSource
import com.repository.media.mapper.search.toCategoryUserInteractionEntity
import com.repository.media.mapper.search.toCategoryUserInteractionModel

class GenresInteractionRepositoryImpl(
    private val dataSource: GenresInteractionDataSource
) : GenresInteractionRepository {
    override suspend fun upsertInteraction(interaction: GenreUserInteraction) =
        dataSource.upsertInteraction(interaction.toCategoryUserInteractionEntity())

    override suspend fun getCategoryInteractions(genreId: Int): Int? =
        dataSource.getCategoryInteractions(genreId)

    override suspend fun getAllInteractions(): List<GenreUserInteraction> =
        dataSource.getAllInteractions().map { it.toCategoryUserInteractionModel() }
}
