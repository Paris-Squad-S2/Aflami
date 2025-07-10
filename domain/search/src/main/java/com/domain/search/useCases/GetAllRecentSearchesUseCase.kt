package com.domain.search.useCases

import com.domain.search.model.SearchHistoryModel
import com.domain.search.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    operator fun invoke(): Flow<List<SearchHistoryModel>> {
        return searchHistoryRepository.getAllSearchHistory()
    }
}