package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.SearchHistoryModel
import com.paris_2.domain.media.entity.SearchType
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getAllSearchHistory(): Flow<List<SearchHistoryModel>>
    suspend fun addSearchHistory(searchTitle: String, searchType: SearchType)
    suspend fun clearSearchHistory(query: String, searchType: SearchType)
    suspend fun clearAllSearchHistory()
}