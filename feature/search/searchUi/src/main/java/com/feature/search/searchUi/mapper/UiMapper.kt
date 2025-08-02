package com.feature.search.searchUi.mapper

import CategoryUiState
import MediaTypeUi
import MediaUiState
import SearchHistoryUiState
import SearchTypeUi
import com.domain.media.entity.Category
import com.domain.media.entity.Media
import com.domain.media.entity.MediaType
import com.domain.media.entity.SearchHistoryModel
import com.domain.media.entity.SearchType


fun List<MediaUiState>.toDomainList() = this.map { it.toDomainModel() }

fun MediaUiState.toDomainModel(): Media {
    return Media(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toDomainModel(),
        categoryIds = this.categories,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating
    )
}

fun MediaTypeUi.toDomainModel(): MediaType {
    return when (this) {
        MediaTypeUi.TVSHOW -> MediaType.TVSHOW
        MediaTypeUi.MOVIE -> MediaType.MOVIE
    }
}

fun List<Media>.toMediaUiList() = this.map { it.toUi() }

fun Media.toUi(): MediaUiState {
    return MediaUiState(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toUi(),
        categories = this.categoryIds,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating,
    )
}

fun MediaType.toUi(): MediaTypeUi {
    return when (this) {
        MediaType.TVSHOW -> MediaTypeUi.TVSHOW
        MediaType.MOVIE -> MediaTypeUi.MOVIE
    }
}

fun List<Category>.toCategoryUiList() = this.map { it.toUi() }

fun Category.toUi(): CategoryUiState {
    return CategoryUiState(
        id = this.id,
        name = this.name
    )
}


fun List<SearchHistoryModel>.toSearchHistoryUiList(): List<SearchHistoryUiState> =
    this.map { it.toSearchHistoryUiState() }

fun SearchHistoryModel.toSearchHistoryUiState(): SearchHistoryUiState {
    return SearchHistoryUiState(
        searchTitle = this.searchTitle,
        searchDate = this.searchDate,
        searchType = searchType.toUi()
    )
}


fun SearchType.toUi(): SearchTypeUi {
    return when (this) {
        SearchType.Query -> SearchTypeUi.Query
        SearchType.Country -> SearchTypeUi.Country
        SearchType.Actor -> SearchTypeUi.Actor
    }
}

fun SearchTypeUi.toDomainModel(): SearchType {
    return when (this) {
        SearchTypeUi.Query -> SearchType.Query
        SearchTypeUi.Country -> SearchType.Country
        SearchTypeUi.Actor -> SearchType.Actor
    }
}
