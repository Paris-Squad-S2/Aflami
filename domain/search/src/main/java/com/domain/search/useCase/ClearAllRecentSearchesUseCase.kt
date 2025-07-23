package com.domain.search.useCase

import com.domain.search.repository.SearchHistoryRepository

class ClearAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke() {
        searchHistoryRepository.clearAllSearchHistory()
    }
}