package com.repository.search.dataSource.local

import com.repository.search.entity.SearchHistoryEntity

interface HistoryLocalDataSource {
    suspend fun addSearchQuery(title: String)
    suspend fun getAllSearchQueries(): List<SearchHistoryEntity>
    suspend fun getSearchHistoryQuery(query: String): SearchHistoryEntity?
    suspend fun clearSearchQueryByQuery(query: String)
    suspend fun clearAll()
}