package com.datasource.local.search.datasource

import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.entity.SearchType
import kotlinx.coroutines.flow.Flow

class HistoryLocalDataSourceImpl(
    private val dao: SearchHistoryDao
) : HistoryLocalDataSource {
    override suspend fun addSearchQuery(title: String, searchType: SearchType) {
        val entity = SearchHistoryEntity(
            searchQuery = title,
            searchType = searchType
        )
        dao.addSearchQuery(entity)
    }

    override fun getAllSearchQueries(): Flow<List<SearchHistoryEntity>> {
        return dao.getAllSearchQueries()
    }

    override suspend fun getSearchHistoryQuery(query: String, searchType: SearchType): SearchHistoryEntity? {
        return dao.getSearchHistoryQuery(query, searchType)
    }

    override suspend fun clearSearchQueryByQuery(query: String, searchType: SearchType) {
        dao.clearSearchQueryByQuery(query, searchType)
    }

    override suspend fun clearAll() {
        dao.clearAllSearchQueries()
    }
}
