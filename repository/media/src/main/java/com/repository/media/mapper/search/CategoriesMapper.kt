package com.repository.media.mapper.search

import com.paris_2.domain.media.entity.Category
import com.repository.media.dto.GenreDto


fun List<GenreDto>.toCategories(): List<Category> {
    return this.map { it.toCategoryModel() }
}

fun GenreDto.toCategoryModel(): Category {
    return Category(
        id = this.id ?: 0,
        name = this.name.orEmpty()
    )
}