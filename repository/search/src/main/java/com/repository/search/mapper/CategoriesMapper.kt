package com.repository.search.mapper

import com.domain.media.model.Category
import com.repository.search.dto.GenreDto
import com.repository.search.entity.GenreEntity

fun List<GenreEntity>.toCategories(): List<Category> {
    return this.map { it.toCategoryModel() }
}

fun GenreEntity.toCategoryModel(): Category {
    return Category(
        id = this.id,
        name = this.name
    )
}

fun GenreDto.toEntity(language: String): GenreEntity {
    return GenreEntity(
        id = this.id ?: 0,
        name = this.name.orEmpty(),
        language = language
    )
}