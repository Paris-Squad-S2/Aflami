package com.domain.search.repository

import com.domain.search.model.CategoryModel

interface CategoriesRepository {
    suspend fun getAllCategories(): List<CategoryModel>
}