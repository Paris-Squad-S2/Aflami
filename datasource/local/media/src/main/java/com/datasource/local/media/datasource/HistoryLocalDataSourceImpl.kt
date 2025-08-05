package com.datasource.local.media.datasource

import com.datasource.local.media.dao.SearchHistoryDao
import com.repository.media.datasource.local.HistoryLocalDataSource
import com.repository.media.entity.SearchHistoryEntity
import com.repository.media.entity.SearchType
import kotlinx.coroutines.flow.Flow

class HistoryLocalDataSourceImpl (
    private val dao: SearchHistoryDao,
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
