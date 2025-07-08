package com.domain.search.useCases

class ClearRecentSearch (
    // private val searchHistoryRepository: SearchHistoryRepository
){
    suspend operator fun invoke(searchHistoryId: String) {
        TemporaryFakeData.searchHistoryList.removeIf { it.id.toString() == searchHistoryId }
        // searchHistoryRepository.clearSearchHistory(searchHistoryId)
    }
}


