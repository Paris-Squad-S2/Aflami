package com.paris.domain.media.useCase

import com.paris.domain.media.repository.SearchHistoryRepository

class ClearAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke() {
        searchHistoryRepository.clearAllSearchHistory()
    }
}