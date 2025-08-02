package com.domain.media.useCase

import com.domain.media.model.SearchHistoryModel
import com.domain.media.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllRecentSearchesUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    operator fun invoke(): Flow<List<SearchHistoryModel>> {
        return searchHistoryRepository.getAllSearchHistory()
    }
}