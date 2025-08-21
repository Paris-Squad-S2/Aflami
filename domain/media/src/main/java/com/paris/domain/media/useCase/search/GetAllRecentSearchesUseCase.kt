package com.paris.domain.media.useCase.search

import com.paris.domain.media.entity.SearchHistoryModel
import com.paris.domain.media.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    operator fun invoke(): Flow<List<SearchHistoryModel>> {
        return searchHistoryRepository.getAllSearchHistory()
    }
}