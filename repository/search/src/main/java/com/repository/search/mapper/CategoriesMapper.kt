package com.repository.search.mapper

import com.domain.search.model.CategoryModel
import com.repository.search.dto.GenreDto
import com.repository.search.entity.GenreEntity

fun List<GenreEntity>.toCategories(): List<CategoryModel> {
    return this.map { it.toCategoryModel() }
}

fun GenreEntity.toCategoryModel(): CategoryModel {
    return CategoryModel(
        id = this.id.toInt(), // need to change
        name = this.name
    )
}

fun GenreDto.toEntity(): GenreEntity {
    return GenreEntity(
        id = this.id.toString(),
        name = this.name.orEmpty()
    )
}