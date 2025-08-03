package com.repository.media.mapper

import com.paris_2.domain.media.entity.Category
import com.repository.media.dto.GenreDto
import com.repository.media.dto.GenresDto


fun GenreDto.toCategory(): Category {
    return Category(
        id = this.id ?: 0,
        name = this.name.orEmpty()
    )
}

fun List<GenreDto>.toCategoryList(): List<Category> {
    return this.map { it.toCategory() }
}

fun GenresDto.toCategoryList(): List<Category> {
    return this.genreDto?.toCategoryList() ?: emptyList()
}
