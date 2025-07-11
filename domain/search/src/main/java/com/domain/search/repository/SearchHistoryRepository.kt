package com.domain.search.repository

import com.domain.search.model.SearchHistoryModel
import com.domain.search.model.SearchType
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun getAllSearchHistory(): Flow<List<SearchHistoryModel>>
    suspend fun addSearchHistory(searchTitle: String, searchType: SearchType)
    suspend fun clearSearchHistory(query:String, searchType: SearchType)
    suspend fun clearAllSearchHistory()
}