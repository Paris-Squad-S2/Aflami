package com.domain.media.repository

import com.domain.media.model.Category

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}