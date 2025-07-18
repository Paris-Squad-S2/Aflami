package com.feature.search.searchUi.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<Media: Any>(
    protected val query: String,
    protected  val searchUseCase:suspend (query: String,page:Int)-> List<Media>
): PagingSource<Int,Media>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        val page = params.key ?: 1
        return try {
            val response = searchUseCase(query,page)
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
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            (anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1))
        }
    }
}

