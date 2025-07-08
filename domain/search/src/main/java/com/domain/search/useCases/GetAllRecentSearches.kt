package com.domain.search.useCases

import com.domain.search.model.SearchHistoryModel

class GetAllRecentSearches (
    // private val searchHistoryRepository: SearchHistoryRepository
){
    suspend operator fun invoke(): List<SearchHistoryModel> {
        return TemporaryFakeData.searchHistoryList
        // return searchHistoryRepository.getAllSearchHistory()
    }
}