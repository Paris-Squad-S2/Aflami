package com.domain.home.repository

import com.domain.home.model.Category

interface MoviesCategoriesRepository {
    suspend fun getMoviesCategories(): List<Category>
}