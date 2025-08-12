package com.feature.categories.categoriesUi.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PagingSource<T: Any>(
     val mediaUseCase:suspend (page:Int)-> List<T>
): PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            val response = mediaUseCase(page)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            (anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1))
        }
    }
}

