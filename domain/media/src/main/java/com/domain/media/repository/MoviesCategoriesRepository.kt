package com.domain.media.repository

import com.domain.media.entity.Category

interface MoviesCategoriesRepository {
    suspend fun getMoviesCategories(): List<Category>
}