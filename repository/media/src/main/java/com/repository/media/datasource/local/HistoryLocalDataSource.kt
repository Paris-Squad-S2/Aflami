package com.repository.media.datasource.local

import com.repository.media.entity.SearchType
import com.repository.media.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryLocalDataSource {
    suspend fun addSearchQuery(title: String, searchType: SearchType)
    fun getSearchQueries(): Flow<List<SearchHistoryEntity>>
    suspend fun getSearchHistoryQuery(query: String, searchType: SearchType): SearchHistoryEntity?
    suspend fun clearSearchByQuery(query: String, searchType: SearchType)
    suspend fun clearSearchQueries()
}