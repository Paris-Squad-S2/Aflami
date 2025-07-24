package com.repository.search.mapper

import com.domain.search.model.GenreUserInteraction
import com.repository.search.entity.GenreUserInteractionEntity

fun GenreUserInteractionEntity.toCategoryUserInteractionModel(): GenreUserInteraction {
    return GenreUserInteraction(
        genreId = this.genreId,
        interactionCount = this.interactionCount
    )
}

fun GenreUserInteraction.toCategoryUserInteractionEntity(): GenreUserInteractionEntity {
    return GenreUserInteractionEntity(
        genreId = this.genreId,
        interactionCount = this.interactionCount
    )
}