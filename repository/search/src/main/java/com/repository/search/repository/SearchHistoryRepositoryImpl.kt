package com.repository.search.repository

import com.domain.search.model.SearchHistoryModel
import com.domain.search.model.SearchType
import com.domain.search.repository.SearchHistoryRepository
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.mapper.toRepositorySearchType
import com.repository.search.mapper.toSearchHistories
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val historyLocalDataSource: HistoryLocalDataSource,
) : SearchHistoryRepository {
    override fun getAllSearchHistory(): Flow<List<SearchHistoryModel>> {
        return historyLocalDataSource.getAllSearchQueries().toSearchHistories()
    }

    override suspend fun addSearchHistory(searchTitle: String, searchType: SearchType) {
        return historyLocalDataSource.addSearchQuery(title = searchTitle, searchType.toRepositorySearchType())
    }

    override suspend fun clearSearchHistory(query: String, searchType: SearchType) {
        return historyLocalDataSource.clearSearchQueryByQuery(query, searchType.toRepositorySearchType())
    }

    override suspend fun clearAllSearchHistory() {
        return historyLocalDataSource.clearAll()
    }
}

