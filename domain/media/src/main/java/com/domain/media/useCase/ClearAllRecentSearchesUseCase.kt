package com.domain.media.useCase

import com.domain.media.repository.SearchHistoryRepository

class ClearAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke() {
        searchHistoryRepository.clearAllSearchHistory()
    }
}