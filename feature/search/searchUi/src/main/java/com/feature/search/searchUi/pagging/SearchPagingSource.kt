package com.feature.search.searchUi.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class SearchPagingSource<Media: Any>(
    val searchUseCase:suspend (page:Int)-> List<Media>
): PagingSource<Int,Media>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        val page = params.key ?: 1
        return try {
            val response = searchUseCase(page)
            LoadResult.Page(
                data = response as List<Media>,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition
    }
}

