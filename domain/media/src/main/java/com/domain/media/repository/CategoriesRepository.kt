package com.domain.media.repository

import com.domain.media.entity.Category

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}