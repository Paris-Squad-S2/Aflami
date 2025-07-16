package com.repository.search.mapper

import com.domain.search.model.CategoryModel
import com.repository.search.dto.GenreDto
import com.repository.search.entity.GenreEntity

fun List<GenreEntity>.toCategories(): List<CategoryModel> {
    return this.map { it.toCategoryModel() }
}

fun GenreEntity.toCategoryModel(): CategoryModel {
    return CategoryModel(
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