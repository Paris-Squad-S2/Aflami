package com.domain.search.useCases

import com.domain.search.repository.SearchHistoryRepository

class ClearAllRecentSearches(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke() {
        searchHistoryRepository.clearAllSearchHistory()
    }
}