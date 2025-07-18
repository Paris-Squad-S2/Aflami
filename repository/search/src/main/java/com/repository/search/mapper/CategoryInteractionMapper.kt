package com.repository.search.mapper

import com.domain.search.model.GenreUserInteractionModel
import com.repository.search.entity.GenreUserInteractionEntity

fun GenreUserInteractionEntity.toCategoryUserInteractionModel(): GenreUserInteractionModel {
    return GenreUserInteractionModel(
        genreId = this.genreId,
        interactionCount = this.interactionCount
    )
}

fun GenreUserInteractionModel.toCategoryUserInteractionEntity(): GenreUserInteractionEntity {
    return GenreUserInteractionEntity(
        genreId = this.genreId,
        interactionCount = this.interactionCount
    )
}