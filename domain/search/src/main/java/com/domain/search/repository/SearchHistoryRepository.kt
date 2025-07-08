package com.domain.search.repository

import com.domain.search.model.Media
import com.domain.search.model.SearchHistoryModel

interface SearchHistoryRepository {
    suspend fun getAllSearchHistory(): List<SearchHistoryModel>
    suspend fun insertSearchHistory(searchHistoryModel: SearchHistoryModel)
    suspend fun deleteSearchHistory(searchHistoryModel: SearchHistoryModel)
}