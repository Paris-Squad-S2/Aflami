package com.repository.media.mapper.search

import com.paris_2.domain.media.entity.GenreUserInteraction
import com.repository.media.entity.GenreUserInteractionEntity

fun GenreUserInteractionEntity.toCategoryUserInteractionModel(): GenreUserInteraction {
    return GenreUserInteraction(
        genre = genreFromId(genreId),
        interactionCount = this.interactionCount
    )
}

fun GenreUserInteraction.toCategoryUserInteractionEntity(): GenreUserInteractionEntity {
    return GenreUserInteractionEntity(
        genreId = genreToId(genre),
        interactionCount = this.interactionCount
    )
}