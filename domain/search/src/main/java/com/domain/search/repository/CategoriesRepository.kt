package com.domain.search.repository

import com.domain.search.model.CategoryModel

interface CategoriesRepository {
    suspend fun getAllCategories(): List<CategoryModel>
}

class CategoriesRepositoryFakeImpl : CategoriesRepository {
    private val categories = listOf(
        CategoryModel(
            id = 1,
            name = "Action",
        ),
        CategoryModel(
            id = 2,
            name = "Comedy",
        ),
        CategoryModel(
            id = 3,
            name = "Drama",
        ),
        CategoryModel(
            id = 4,
            name = "Horror",
        ),
        CategoryModel(
            id = 5,
            name = "Sci-Fi",
        ),
        CategoryModel(
            id = 6,
            name = "Romance",
        ),
    )

    override suspend fun getAllCategories(): List<CategoryModel> {
        return categories
    }
}