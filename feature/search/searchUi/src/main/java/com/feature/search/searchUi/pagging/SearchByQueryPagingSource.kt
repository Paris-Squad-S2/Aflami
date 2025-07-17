package com.feature.search.searchUi.pagging

import androidx.paging.PagingState
import com.domain.search.useCases.SearchByQueryUseCase
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.screen.search.MediaUiState

class SearchByQueryPagingSource(
    query: String,
    private val searchByQueryUseCase: SearchByQueryUseCase,
): BasePagingSource<MediaUiState>(
    searchUseCase = { query, page ->
        searchByQueryUseCase(query,page).toMediaUiList()
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