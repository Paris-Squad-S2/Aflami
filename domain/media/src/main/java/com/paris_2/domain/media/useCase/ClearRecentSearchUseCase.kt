package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.SearchType
import com.paris_2.domain.media.repository.SearchHistoryRepository

class ClearRecentSearchUseCase(
    private val searchHistoryRepository: SearchHistoryRepository,
) {
    suspend operator fun invoke(query:String, searchType: SearchType) {
        searchHistoryRepository.clearSearchHistory(query, searchType)
    }
}