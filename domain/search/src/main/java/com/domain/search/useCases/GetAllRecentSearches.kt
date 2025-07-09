package com.domain.search.useCases

import com.domain.search.model.SearchHistoryModel
import com.domain.search.repository.SearchHistoryRepository

class GetAllRecentSearches(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke(): List<SearchHistoryModel> {
        return searchHistoryRepository.getAllSearchHistory()
    }
}