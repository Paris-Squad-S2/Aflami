package com.domain.search.repository

import com.domain.search.model.Category

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}