package com.domain.search.useCases

class ClearAllRecentSearches (
    // private val searchHistoryRepository: SearchHistoryRepository
){
    suspend operator fun invoke() {
        TemporaryFakeData.searchHistoryList.clear()
        // searchHistoryRepository.clearAllSearchHistory()
    }
}