package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Category

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Category>
}