package com.domain.search.useCases

import com.domain.search.model.SearchHistoryModel
import com.domain.search.repository.SearchHistoryRepository

class GetAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke(): List<SearchHistoryModel> {
        return searchHistoryRepository.getAllSearchHistory()
    }
}