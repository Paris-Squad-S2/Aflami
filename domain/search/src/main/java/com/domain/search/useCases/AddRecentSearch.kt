package com.domain.search.useCases

import com.domain.search.model.SearchHistoryModel

class AddRecentSearch (
//    private val searchHistoryRepository: SearchHistoryRepository
){

    suspend operator fun invoke(searchTitle: String) {
        val newSearchHistory = SearchHistoryModel(
            id = TemporaryFakeData.searchHistoryList.size.toLong() + 1,
            searchTitle = searchTitle
        )
        TemporaryFakeData.searchHistoryList.add(newSearchHistory)
        // searchHistoryRepository.addSearchHistory(newSearchHistory)
    }
}