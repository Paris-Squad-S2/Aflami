package com.repository.search.mapper

import com.domain.search.model.SearchHistoryModel
import com.domain.search.model.SearchType
import com.repository.search.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun Flow<List<SearchHistoryEntity>>.toSearchHistories(): Flow<List<SearchHistoryModel>> {
    return this.map { entities -> entities.map { it.toSearchHistoryModel() } }
}

fun SearchHistoryEntity.toSearchHistoryModel(): SearchHistoryModel {
    return SearchHistoryModel(
        searchTitle = this.searchQuery,
        searchDate = this.searchDate.toString(),
        searchType = this.searchType.toDomainSearchType()
    )
}

fun SearchType.toRepositorySearchType(): com.repository.search.entity.SearchType {
    return when (this) {
        SearchType.Query -> com.repository.search.entity.SearchType.Query
        SearchType.Country -> com.repository.search.entity.SearchType.Country
        SearchType.Actor -> com.repository.search.entity.SearchType.Actor
    }
}

fun com.repository.search.entity.SearchType.toDomainSearchType(): SearchType {
    return when (this) {
        com.repository.search.entity.SearchType.Query -> SearchType.Query
        com.repository.search.entity.SearchType.Country -> SearchType.Country
        com.repository.search.entity.SearchType.Actor -> SearchType.Actor
    }
}
