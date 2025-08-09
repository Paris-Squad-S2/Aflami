package com.repository.media.datasource.local

import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity

interface HomeMediaLocalDataSource {
    suspend fun addHomeMedia(mediaList: List<HomeMediaEntity>)
    suspend fun getHomeMediaByCategory(category: Category, language: String): List<HomeMediaEntity>
    suspend fun clearHomeMediaByCategory(category: Category)
}