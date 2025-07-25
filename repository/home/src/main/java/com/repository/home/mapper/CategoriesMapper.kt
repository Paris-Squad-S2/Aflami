package com.repository.home.mapper

import com.domain.home.model.Category
import com.repository.home.dto.GenreDto
import com.repository.home.dto.GenresDto


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
