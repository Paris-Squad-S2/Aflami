package com.domain.search.repository

import com.domain.search.model.CategoryModel

interface CategoriesRepository {
    suspend fun getAllCategories(): List<CategoryModel>
}

class CategoriesRepositoryFakeImpl : CategoriesRepository {
    private val categories = listOf(
        CategoryModel(28, "Action"),
        CategoryModel(12, "Adventure"),
        CategoryModel(16, "Animation"),
        CategoryModel(35, "Comedy"),
        CategoryModel(80, "Crime"),
        CategoryModel(99, "Documentary"),
        CategoryModel(18, "Drama"),
        CategoryModel(10751, "Family"),
        CategoryModel(14, "Fantasy"),
        CategoryModel(36, "History"),
        CategoryModel(27, "Horror"),
        CategoryModel(10402, "Music"),
        CategoryModel(9648, "Mystery"),
        CategoryModel(10749, "Romance"),
        CategoryModel(878, "Science Fiction"),
        CategoryModel(10770, "TV Movie"),
        CategoryModel(53, "Thriller"),
        CategoryModel(10752, "War"),
        CategoryModel(37, "Western"),

        CategoryModel(10759, "Action & Adventure"),
        CategoryModel(10762, "Kids"),
        CategoryModel(10763, "News"),
        CategoryModel(10764, "Reality"),
        CategoryModel(10765, "Sci-Fi & Fantasy"),
        CategoryModel(10766, "Soap"),
        CategoryModel(10767, "Talk"),
        CategoryModel(10768, "War & Politics")
    )

    override suspend fun getAllCategories(): List<CategoryModel> {
        return categories
    }
}