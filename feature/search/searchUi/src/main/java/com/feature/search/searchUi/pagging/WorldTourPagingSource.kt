package com.feature.search.searchUi.pagging

import MediaUiState
import androidx.paging.PagingState
import com.domain.search.useCase.GetMoviesOnlyByCountryNameUseCase
import com.domain.search.useCase.SortingMediaByCategoriesInteractionUseCase
import com.feature.search.searchUi.mapper.toMediaUiList

class WorldTourPagingSource(
    countryName: String,
    private val getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase

): BasePagingSource<MediaUiState>(
    searchUseCase = { query, page ->
        sortingMediaByCategoriesInteractionUseCase(getMoviesByCountryUseCase(query,page)).toMediaUiList()
    },
    query = countryName
) {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaUiState> {
        return super.load(params)
    }

    override fun getRefreshKey(state: PagingState<Int,MediaUiState>): Int? {
        return super.getRefreshKey(state)
    }

}