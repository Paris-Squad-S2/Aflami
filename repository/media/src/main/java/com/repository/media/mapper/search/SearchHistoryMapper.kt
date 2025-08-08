package com.repository.media.mapper.search


import com.paris_2.domain.media.entity.SearchHistoryModel
import com.repository.media.entity.SearchHistoryEntity
import com.repository.media.entity.SearchType
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

fun com.paris_2.domain.media.entity.SearchType.toRepositorySearchType(): SearchType {
    return when (this) {
        com.paris_2.domain.media.entity.SearchType.Query -> SearchType.Query
        com.paris_2.domain.media.entity.SearchType.Country -> SearchType.Country
        com.paris_2.domain.media.entity.SearchType.Actor -> SearchType.Actor
    }
}

fun SearchType.toDomainSearchType(): com.paris_2.domain.media.entity.SearchType {
    return when (this) {
        SearchType.Query -> com.paris_2.domain.media.entity.SearchType.Query
        SearchType.Country  -> com.paris_2.domain.media.entity.SearchType.Country
        SearchType.Actor -> com.paris_2.domain.media.entity.SearchType.Actor
    }
}
