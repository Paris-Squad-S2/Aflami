package com.repository.media.repository

import com.paris_2.domain.media.entity.SearchHistoryModel
import com.paris_2.domain.media.entity.SearchType
import com.paris_2.domain.media.repository.SearchHistoryRepository
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.mapper.search.toRepositorySearchType
import com.repository.media.mapper.search.toSearchHistories
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
