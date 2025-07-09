package com.domain.search.repository

import com.domain.search.model.SearchHistoryModel

interface SearchHistoryRepository {
    suspend fun getAllSearchHistory(): List<SearchHistoryModel>
    suspend fun addSearchHistory(searchTitle: String)
    suspend fun clearSearchHistory(query:String)
    suspend fun clearAllSearchHistory()
}