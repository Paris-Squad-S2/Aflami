package com.repository.search.mapper

import com.domain.search.model.SearchHistoryModel
import com.repository.search.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun Flow<List<SearchHistoryEntity>>.toSearchHistories(): Flow<List<SearchHistoryModel>> {
    return this.map { entities -> entities.map { it.toSearchHistoryModel() } }
}

fun SearchHistoryEntity.toSearchHistoryModel(): SearchHistoryModel {
    return SearchHistoryModel(

        searchTitle = this.searchQuery,
        searchDate = this.searchDate.toString()
    )
}