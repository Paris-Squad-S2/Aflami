package com.feature.search.searchUi.pagging

import com.domain.search.useCases.SearchByQueryUseCase
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.screen.search.MediaUiState

class SearchByQueryPagingSource(
    private val query: String,
    private val searchByQueryUseCase: SearchByQueryUseCase,
): DefaultPagingSource<MediaUiState>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaUiState> {
        val page = params.key ?: 1
        return try {
            val response = searchByQueryUseCase(query = query,page = page).toMediaUiList()
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}