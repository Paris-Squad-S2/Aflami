package com.domain.search.repository

import com.domain.search.model.SearchHistoryModel

interface SearchHistoryRepository {
    suspend fun getAllSearchHistory(): List<SearchHistoryModel>
    suspend fun addSearchHistory(searchTitle: String)
    suspend fun clearSearchHistory(searchHistoryId: Long)
    suspend fun clearAllSearchHistory()
}

class SearchHistoryRepositoryFakeImpl : SearchHistoryRepository {
    private val searchHistoryList = mutableListOf(
        SearchHistoryModel(
            id = 1,
            searchTitle = "Example Movie 1",
        ),
        SearchHistoryModel(
            id = 2,
            searchTitle = "Example TV Show 1",
        ),
        SearchHistoryModel(
            id = 3,
            searchTitle = "Example Movie 2",
        ),
        SearchHistoryModel(
            id = 4,
            searchTitle = "Example TV Show 2",
        ),
        SearchHistoryModel(
            id = 5,
            searchTitle = "Example Movie 3",
        ),
        SearchHistoryModel(
            id = 6,
            searchTitle = "Example TV Show 3",
        ),
        SearchHistoryModel(
            id = 7,
            searchTitle = "Example Movie 4",
        ),
        SearchHistoryModel(
            id = 8,
            searchTitle = "Example TV Show 4",
        ),
        SearchHistoryModel(
            id = 9,
            searchTitle = "Example Movie 5",
        ),
        SearchHistoryModel(
            id = 10,
            searchTitle = "Example TV Show 5",
        ),
        SearchHistoryModel(
            id = 11,
            searchTitle = "Example Movie 6",
        ),
        SearchHistoryModel(
            id = 12,
            searchTitle = "Example TV Show 6",
        ),
        SearchHistoryModel(
            id = 13,
            searchTitle = "Example Movie 7",
        ),
        SearchHistoryModel(
            id = 14,
            searchTitle = "Example TV Show 7",
        ),
        SearchHistoryModel(
            id = 15,
            searchTitle = "Example Movie 8",
        ),
        SearchHistoryModel(
            id = 16,
            searchTitle = "Example TV Show 8",
        ),
        SearchHistoryModel(
            id = 17,
            searchTitle = "Example Movie 9",
        ),
        SearchHistoryModel(
            id = 18,
            searchTitle = "Example TV Show 9",
        ),
        SearchHistoryModel(
            id = 19,
            searchTitle = "Example Movie 10",
        ),
        SearchHistoryModel(
            id = 20,
            searchTitle = "Example TV Show 10",
        ),
        SearchHistoryModel(
            id = 21,
            searchTitle = "Example Movie 11",
        ),
        SearchHistoryModel(
            id = 22,
            searchTitle = "Example TV Show 11",
        ),
        SearchHistoryModel(
            id = 23,
            searchTitle = "Example Movie 12",
        ),
        SearchHistoryModel(
            id = 24,
            searchTitle = "Example TV Show 12",
        ),
        SearchHistoryModel(
            id = 25,
            searchTitle = "Example Movie 13",
        ),
    )

    override suspend fun getAllSearchHistory(): List<SearchHistoryModel> {
        return searchHistoryList.toList()
    }

    override suspend fun addSearchHistory(searchTitle: String) {
        val newId = (searchHistoryList.maxOfOrNull { it.id } ?: 0) + 1
        searchHistoryList.add(SearchHistoryModel(id = newId, searchTitle = searchTitle))
    }


    override suspend fun clearSearchHistory(searchHistoryId: Long) {
        searchHistoryList.removeIf { it.id == searchHistoryId }
    }

    override suspend fun clearAllSearchHistory() {
        searchHistoryList.clear()
    }
}
