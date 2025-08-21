package com.paris.domain.media.repository

import com.paris.domain.media.entity.Category

interface MoviesCategoriesRepository {
    suspend fun getMoviesCategories(): List<Category>
}