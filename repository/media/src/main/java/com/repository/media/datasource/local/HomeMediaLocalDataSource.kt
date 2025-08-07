package com.repository.media.datasource.local

import com.repository.media.entity.Category
import com.repository.media.entity.HomeMediaEntity

interface HomeMediaLocalDataSource {
    suspend fun addMediaList(mediaList: List<HomeMediaEntity>)
    suspend fun getMediaListByCategory(category: Category): List<HomeMediaEntity>
    suspend fun clearMediaByCategory(category: Category)
}