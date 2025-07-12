package com.domain.search.useCases

import com.domain.search.model.SearchType
import com.domain.search.repository.SearchHistoryRepository

class ClearRecentSearchUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke(query:String, searchType: SearchType) {
        searchHistoryRepository.clearSearchHistory(query, searchType)
    }
}