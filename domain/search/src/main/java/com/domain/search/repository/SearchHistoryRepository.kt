package com.domain.search.repository

import com.domain.search.model.SearchHistoryModel
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getAllSearchHistory(): Flow<List<SearchHistoryModel>>
    suspend fun addSearchHistory(searchTitle: String)
    suspend fun clearSearchHistory(query: String)
    suspend fun clearAllSearchHistory()
}