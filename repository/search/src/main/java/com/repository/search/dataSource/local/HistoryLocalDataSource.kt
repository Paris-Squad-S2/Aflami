package com.repository.search.dataSource.local

import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.entity.SearchType
import kotlinx.coroutines.flow.Flow

interface HistoryLocalDataSource {
    suspend fun addSearchQuery(title: String, searchType: SearchType)
    fun getAllSearchQueries(): Flow<List<SearchHistoryEntity>>
    suspend fun getSearchHistoryQuery(query: String, searchType: SearchType): SearchHistoryEntity?
    suspend fun clearSearchQueryByQuery(query: String, searchType: SearchType)
    suspend fun clearAll()
}