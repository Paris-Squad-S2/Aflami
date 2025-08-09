package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Genre

interface CategoriesRepository {
    suspend fun getAllCategories(): List<Genre>
}