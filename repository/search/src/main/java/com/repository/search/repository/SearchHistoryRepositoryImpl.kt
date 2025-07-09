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

    override suspend fun clearSearchHistory(searchHistoryId: Int) {
        return historyLocalDataSource.clearSearchQueryById(id = searchHistoryId.toLong()) // should use int not long
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
        id = this.id,
        searchTitle = this.searchTitle
    )
}