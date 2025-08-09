package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Genre

interface MoviesCategoriesRepository {
    suspend fun getMoviesCategories(): List<Genre>
}