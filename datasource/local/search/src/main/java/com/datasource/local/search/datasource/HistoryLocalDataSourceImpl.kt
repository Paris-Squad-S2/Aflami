package com.datasource.local.search.datasource

import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

class HistoryLocalDataSourceImpl(
    private val dao: SearchHistoryDao
) : HistoryLocalDataSource {
    override suspend fun addSearchQuery(title: String) {
        val entity = SearchHistoryEntity(
            searchQuery = title
        )
        dao.addSearchQuery(entity)
    }

    override fun getAllSearchQueries(): Flow<List<SearchHistoryEntity>> {
        return dao.getAllSearchQueries()
    }

    override suspend fun getSearchHistoryQuery(query: String): SearchHistoryEntity? {
        return dao.getSearchHistoryQuery(query)
    }

    override suspend fun clearSearchQueryByQuery(query: String) {
        dao.clearSearchQueryByQuery(query)
    }

    override suspend fun clearAll() {
        dao.clearAllSearchQueries()
    }
}
