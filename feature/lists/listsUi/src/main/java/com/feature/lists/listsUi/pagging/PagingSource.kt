package com.feature.lists.listsUi.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Media

class PagingSource<T: Any>(
    private val dataLoader: suspend (page: Int) -> List<T>
): PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            val response = dataLoader(page)
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
        return state.anchorPosition
    }
}
