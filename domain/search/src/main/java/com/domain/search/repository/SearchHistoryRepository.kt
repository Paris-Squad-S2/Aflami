package com.domain.search.repository

import com.domain.search.model.SearchHistoryModel

interface SearchHistoryRepository {
    suspend fun getAllSearchHistory(): List<SearchHistoryModel>
    suspend fun addSearchHistory(searchTitle: String)
    suspend fun clearSearchHistory(query: String)
    suspend fun clearAllSearchHistory()
}

class SearchHistoryRepositoryFakeImpl : SearchHistoryRepository {
    private val searchHistoryList = mutableListOf(
        SearchHistoryModel(
            searchTitle = "Example Movie 1",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 1",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 2",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 2",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 3",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 3",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 4",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 4",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 5",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 5",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 6",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 6",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 7",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 7",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 8",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 8",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 9",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 9",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 10",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 10",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 11",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 11",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 12",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example TV Show 12",
            searchDate = "2023-10-01T12:00:00",
        ),
        SearchHistoryModel(
            searchTitle = "Example Movie 13",
            searchDate = "2023-10-01T12:00:00",
        ),
    )

    override suspend fun getAllSearchHistory(): List<SearchHistoryModel> {
        return searchHistoryList.toList()
    }

    override suspend fun addSearchHistory(searchTitle: String) {
        searchHistoryList.add(
            SearchHistoryModel(
                searchTitle = searchTitle,
                searchDate = "2023-10-01T12:00:00",
            )
        )
    }


    override suspend fun clearSearchHistory(query: String) {
        searchHistoryList.removeIf {
            it.searchTitle == query
        }
    }

    override suspend fun clearAllSearchHistory() {
        searchHistoryList.clear()
    }
}
