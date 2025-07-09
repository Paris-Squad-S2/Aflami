package com.domain.search.useCases

import com.domain.search.repository.SearchHistoryRepository

class AddRecentSearchUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke(searchTitle: String) {
        searchHistoryRepository.addSearchHistory(searchTitle)
    }
}