package com.datasource.local.search

import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.dataSource.SearchHistoryLocalDataSource
import com.repository.search.entity.SearchHistoryEntity

class SearchHistoryLocalDataSourceImpl(
    private val dao: SearchHistoryDao
) : SearchHistoryLocalDataSource {
    override suspend fun addSearchQuery(title: String) {
        val entity = SearchHistoryEntity(
            searchTitle = title
        )
        dao.addSearchQuery(entity)
    }

    override suspend fun getAllSearchQueries(): List<SearchHistoryEntity> {
        return dao.getAllSearchQueries().map { entity ->
            SearchHistoryEntity(
                id = entity.id,
                searchTitle = entity.searchTitle
            )
        }
    }

    override suspend fun clearSearchQueryById(id: Long) {
        dao.clearSearchQueryById(id)
    }

    override suspend fun clearAll() {
        dao.clearAllSearchQueries()
    }
}
