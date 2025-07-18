package com.repository.search.repository

import com.domain.search.model.GenreUserInteractionModel
import com.domain.search.repository.GenresInteractionRepository
import com.repository.search.dataSource.local.GenresInteractionDataSource
import com.repository.search.mapper.toCategoryUserInteractionEntity
import com.repository.search.mapper.toCategoryUserInteractionModel

class GenresInteractionRepositoryImpl(
    private val dataSource: GenresInteractionDataSource
) : GenresInteractionRepository {
    override suspend fun upsertInteraction(interaction: GenreUserInteractionModel) =
        dataSource.upsertInteraction(interaction.toCategoryUserInteractionEntity())

    override suspend fun getCategoryInteractions(genreId: Int): Int? =
        dataSource.getCategoryInteractions(genreId)

    override suspend fun getAllInteractions(): List<GenreUserInteractionModel> =
        dataSource.getAllInteractions().map { it.toCategoryUserInteractionModel() }
}
