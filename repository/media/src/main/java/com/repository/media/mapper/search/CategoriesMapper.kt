package com.repository.media.mapper.search

import com.paris_2.domain.media.entity.Category
import com.repository.media.dto.GenreDto
import com.repository.media.entity.GenreEntity

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