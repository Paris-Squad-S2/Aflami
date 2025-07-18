package com.feature.search.searchUi.pagging

import androidx.paging.PagingState
import com.domain.search.useCases.GetMoviesOnlyByCountryNameUseCase
import com.domain.search.useCases.SortingMediaByCategoriesInteractionUseCase
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.screen.search.MediaUiState

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