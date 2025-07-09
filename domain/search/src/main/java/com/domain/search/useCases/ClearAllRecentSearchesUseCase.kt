package com.domain.search.useCases

import com.domain.search.repository.SearchHistoryRepository

class ClearAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke() {
        searchHistoryRepository.clearAllSearchHistory()
    }
}