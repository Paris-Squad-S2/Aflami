package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Category

interface MoviesCategoriesRepository {
    suspend fun getMoviesCategories(): List<Category>
}