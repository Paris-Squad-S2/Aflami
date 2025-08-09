package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.GenreUserInteraction

interface GenresInteractionRepository {
    suspend fun upsertInteraction(interaction: GenreUserInteraction)
    suspend fun getCategoryInteractions(category: Category): Int?
    suspend fun getAllInteractions(): List<GenreUserInteraction>
}
