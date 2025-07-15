package com.feature.search.searchUi.mapper

import com.domain.search.model.CategoryModel
import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.model.SearchHistoryModel
import com.domain.search.model.SearchType
import com.feature.search.searchUi.screen.search.CategoryUiState
import com.feature.search.searchUi.screen.search.MediaTypeUi
import com.feature.search.searchUi.screen.search.MediaUiState
import com.feature.search.searchUi.screen.search.SearchHistoryUiState
import com.feature.search.searchUi.screen.search.SearchTypeUi


fun List<MediaUiState>.toDomainList() = this.map { it.toDomainModel() }

fun MediaUiState.toDomainModel(): Media{
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

fun MediaTypeUi.toDomainModel(): MediaType{
    return when(this){
        MediaTypeUi.TVSHOW -> MediaType.TVSHOW
        MediaTypeUi.MOVIE -> MediaType.MOVIE
    }
}

fun List<Media>.toMediaUiList() = this.map { it.toUi() }

fun Media.toUi(): MediaUiState{
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

fun MediaType.toUi(): MediaTypeUi{
    return when(this){
        MediaType.TVSHOW -> MediaTypeUi.TVSHOW
        MediaType.MOVIE -> MediaTypeUi.MOVIE
    }
}

fun List<CategoryModel>.toCategoryUiList() = this.map { it.toUi() }

fun CategoryModel.toUi(): CategoryUiState{
    return CategoryUiState(
        id = this.id,
        name = this.name
    )
}


fun List<SearchHistoryModel>.toSearchHistoryUiList(): List<SearchHistoryUiState> = this.map { it.toSearchHistoryUiState() }

fun SearchHistoryModel.toSearchHistoryUiState(): SearchHistoryUiState{
    return SearchHistoryUiState(
        searchTitle = this.searchTitle,
        searchDate = this.searchDate,
        searchType = searchType.toUi()
    )
}


fun SearchType.toUi(): SearchTypeUi{
    return when(this){
        SearchType.Query -> SearchTypeUi.Query
        SearchType.Country -> SearchTypeUi.Country
        SearchType.Actor -> SearchTypeUi.Actor
    }
}

fun SearchTypeUi.toDomainModel(): SearchType{
    return when(this){
        SearchTypeUi.Query -> SearchType.Query
        SearchTypeUi.Country -> SearchType.Country
        SearchTypeUi.Actor -> SearchType.Actor
    }
}
