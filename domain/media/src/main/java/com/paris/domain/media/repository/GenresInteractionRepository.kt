package com.paris.domain.media.repository

import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.GenreUserInteraction

interface GenresInteractionRepository {
    suspend fun upsertInteraction(interaction: GenreUserInteraction)
    suspend fun getCategoryInteractions(category: Category): Int?
    suspend fun getAllInteractions(): List<GenreUserInteraction>
}
