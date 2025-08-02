package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.repository.SearchHistoryRepository

class ClearAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke() {
        searchHistoryRepository.clearAllSearchHistory()
    }
}