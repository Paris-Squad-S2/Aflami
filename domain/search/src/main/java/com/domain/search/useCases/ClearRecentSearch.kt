package com.domain.search.useCases

import com.domain.search.repository.SearchHistoryRepository

class ClearRecentSearch(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke(searchHistoryId: Int) {
        searchHistoryRepository.clearSearchHistory(searchHistoryId)
    }
}


