package com.repository.media.repository

import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.GenreUserInteraction
import com.paris.domain.media.repository.GenresInteractionRepository
import com.repository.media.datasource.local.GenresInteractionDataSource
import com.repository.media.mapper.search.toCategoryUserInteractionEntity
import com.repository.media.mapper.search.toCategoryUserInteractionModel
import com.repository.media.mapper.search.toId

class GenresInteractionRepositoryImpl(
    private val dataSource: GenresInteractionDataSource
) : GenresInteractionRepository {
    override suspend fun upsertInteraction(interaction: GenreUserInteraction) =
        dataSource.upsertGenresInteraction(interaction.toCategoryUserInteractionEntity())

    override suspend fun getCategoryInteractions(category: Category): Int? =
        dataSource.getCategoryByGenreId(category.toId())

    override suspend fun getAllInteractions(): List<GenreUserInteraction> =
        dataSource.getGenresInteractions().map { it.toCategoryUserInteractionModel() }
}
