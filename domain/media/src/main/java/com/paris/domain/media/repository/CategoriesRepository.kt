package com.paris.domain.media.repository

import com.paris.domain.media.entity.Category

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}