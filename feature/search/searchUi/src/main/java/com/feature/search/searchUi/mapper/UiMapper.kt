package com.feature.search.searchUi.mapper

import com.feature.search.searchUi.screen.search.MediaTypeUi
import com.feature.search.searchUi.screen.search.MediaUiState
import com.feature.search.searchUi.screen.search.SearchHistoryUiState
import com.feature.search.searchUi.screen.search.SearchTypeUi
import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.entity.SearchHistoryModel
import com.paris_2.domain.media.entity.SearchType


fun List<MediaUiState>.toDomainList() = this.map { it.toDomainModel() }

fun MediaUiState.toDomainModel(): Media {
    return Media(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toDomainModel(),
        categories = this.categories,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating
    )
}

fun MediaTypeUi.toDomainModel(): MediaType {
    return when (this) {
        MediaTypeUi.TvShow -> MediaType.TvShow
        MediaTypeUi.Movie -> MediaType.Movie
    }
}

fun List<Media>.toMediaUiList() = this.map { it.toUi() }

fun Media.toUi(): MediaUiState {
    return MediaUiState(
        id = this.id,
        imageUri = this.imageUri,
        title = this.title,
        type = this.type.toUi(),
        categories = this.categories,
        yearOfRelease = this.yearOfRelease,
        rating = this.rating,
    )
}

fun MediaType.toUi(): MediaTypeUi {
    return when (this) {
        MediaType.TvShow -> MediaTypeUi.TvShow
        MediaType.Movie -> MediaTypeUi.Movie
    }
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
