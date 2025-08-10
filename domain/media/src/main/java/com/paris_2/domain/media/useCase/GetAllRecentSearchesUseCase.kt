package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.SearchHistoryModel
import com.paris_2.domain.media.repository.SearchHistoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllRecentSearchesUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    operator fun invoke(): Flow<List<SearchHistoryModel>> {
        return searchHistoryRepository.getAllSearchHistory()
    }
}