package com.domain.media.repository

import com.domain.media.entity.SearchHistoryModel
import com.domain.media.entity.SearchType
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getAllSearchHistory(): Flow<List<SearchHistoryModel>>
    suspend fun addSearchHistory(searchTitle: String, searchType: SearchType)
    suspend fun clearSearchHistory(query: String, searchType: SearchType)
    suspend fun clearAllSearchHistory()
}