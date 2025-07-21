package com.domain.home.repository

import com.domain.home.model.Category

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}