package com.repository.search.repository

import com.domain.search.model.SearchHistoryModel
import com.domain.search.repository.SearchHistoryRepository
import com.repository.search.dataSource.HistoryLocalDataSource
import com.repository.search.entity.SearchHistoryEntity

class SearchHistoryRepositoryImpl(
    private val historyLocalDataSource: HistoryLocalDataSource,
) : SearchHistoryRepository {
    override suspend fun getAllSearchHistory(): List<SearchHistoryModel> {
        return historyLocalDataSource.getAllSearchQueries().toSearchHistories()
    }

    override suspend fun addSearchHistory(searchTitle: String) {
        return historyLocalDataSource.addSearchQuery(title = searchTitle)
    }

    override suspend fun clearSearchHistory(query: String) {
        return historyLocalDataSource.clearSearchQueryByQuery(query)
    }

    override suspend fun clearAllSearchHistory() {
        return historyLocalDataSource.clearAll()
    }
}


fun List<SearchHistoryEntity>.toSearchHistories(): List<SearchHistoryModel> {
    return this.map { it.toSearchHistoryModel() }
}

fun SearchHistoryEntity.toSearchHistoryModel(): SearchHistoryModel {
    return SearchHistoryModel(

        searchTitle = this.searchQuery,
        searchDate = this.searchDate.toString()
    )
}