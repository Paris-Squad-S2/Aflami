package com.paris_2.home.mapper

import com.domain.home.model.Category
import com.paris_2.home.dto.GenreDto
import com.paris_2.home.dto.GenresDto

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
