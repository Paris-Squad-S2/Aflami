package com.repository.search.mapper

import com.domain.search.model.CategoryModel
import com.domain.search.model.Media
import com.example.search.mapper.toMediaOrNull
import com.repository.search.dto.Genre
import com.repository.search.dto.SearchDto

fun SearchDto.toMediaList(): List<Media> {
    return this.results?.mapNotNull { it.toMediaOrNull() } ?: emptyList()
}

fun Genre.toCategoryModel(): CategoryModel {
    return CategoryModel(
        id = this.id,
        name = this.name
    )
}

fun List<Genre>.toCategoryModels(): List<CategoryModel> {
    return this.map { it.toCategoryModel() }
}


