package com.repository.search.dataSource.local

import com.repository.search.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryLocalDataSource {
    suspend fun addSearchQuery(title: String)
    fun getAllSearchQueries(): Flow<List<SearchHistoryEntity>>
    suspend fun getSearchHistoryQuery(query: String): SearchHistoryEntity?
    suspend fun clearSearchQueryByQuery(query: String)
    suspend fun clearAll()
}