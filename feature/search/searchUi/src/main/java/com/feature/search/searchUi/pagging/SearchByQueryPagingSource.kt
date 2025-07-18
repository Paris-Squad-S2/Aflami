package com.feature.search.searchUi.pagging

import androidx.paging.PagingState
import com.domain.search.useCases.SearchByQueryUseCase
import com.domain.search.useCases.SortingMediaByCategoriesInteractionUseCase
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.screen.search.MediaUiState

class SearchByQueryPagingSource(
    query: String,
    private val searchByQueryUseCase: SearchByQueryUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase
): BasePagingSource<MediaUiState>(
    searchUseCase = { query, page ->
        sortingMediaByCategoriesInteractionUseCase(searchByQueryUseCase(query,page)).toMediaUiList()
    },
    query = query
) {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaUiState> {
      return super.load(params)
    }

    override fun getRefreshKey(state: PagingState<Int, MediaUiState>): Int? {
        return super.getRefreshKey(state)
    }
}