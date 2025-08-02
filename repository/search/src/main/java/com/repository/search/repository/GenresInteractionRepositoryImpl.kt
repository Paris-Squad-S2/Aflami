package com.repository.search.repository

import com.domain.media.model.GenreUserInteraction
import com.domain.media.repository.GenresInteractionRepository
import com.repository.search.dataSource.local.GenresInteractionDataSource
import com.repository.search.mapper.toCategoryUserInteractionEntity
import com.repository.search.mapper.toCategoryUserInteractionModel

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
