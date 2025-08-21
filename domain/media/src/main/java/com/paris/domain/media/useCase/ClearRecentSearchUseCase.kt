package com.paris.domain.media.useCase

import com.paris.domain.media.entity.SearchType
import com.paris.domain.media.repository.SearchHistoryRepository

class ClearRecentSearchUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke(query:String, searchType: SearchType) {
        searchHistoryRepository.clearSearchHistory(query, searchType)
    }
}