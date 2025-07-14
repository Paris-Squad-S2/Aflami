package com.datasource.local.search.datasource

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.dataSource.local.HistoryLocalDataSource
import com.repository.search.dataSource.local.workManager.ClearMediaWorker
import com.repository.search.entity.SearchHistoryEntity
import com.repository.search.entity.SearchType
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class HistoryLocalDataSourceImpl(
    private val dao: SearchHistoryDao,
    private val workManager: WorkManager
) : HistoryLocalDataSource {
    override suspend fun addSearchQuery(title: String, searchType: SearchType) {
        val entity = SearchHistoryEntity(
            searchQuery = title,
            searchType = searchType
        )
        dao.addSearchQuery(entity)
        scheduleClearMediaWork(title, searchType)
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

    private fun scheduleClearMediaWork(searchQuery: String, searchType: SearchType) {
        val inputData = workDataOf(
            ClearMediaWorker.SEARCH_QUERY to searchQuery,
            ClearMediaWorker.SEARCH_TYPE to searchType.name
        )

        val workRequest = OneTimeWorkRequestBuilder<ClearMediaWorker>()
            .setInputData(inputData)
            .setInitialDelay(1, TimeUnit.HOURS)
            .build()

        workManager.enqueue(workRequest)
    }

}
