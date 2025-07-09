package com.domain.search.useCases

import com.domain.search.repository.SearchHistoryRepository

class ClearRecentSearchUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke(searchHistoryId: Int) {
        searchHistoryRepository.clearSearchHistory(searchHistoryId)
    }
}


