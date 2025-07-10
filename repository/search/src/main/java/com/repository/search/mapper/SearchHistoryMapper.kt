package com.repository.search.mapper

import com.domain.search.model.SearchHistoryModel
import com.repository.search.entity.SearchHistoryEntity


fun List<SearchHistoryEntity>.toSearchHistories(): List<SearchHistoryModel> {
    return this.map { it.toSearchHistoryModel() }
}

fun SearchHistoryEntity.toSearchHistoryModel(): SearchHistoryModel {
    return SearchHistoryModel(

        searchTitle = this.searchQuery,
        searchDate = this.searchDate.toString()
    )
}