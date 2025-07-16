package com.feature.search.searchUi.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class DefaultPagingSource<Media: Any>(
): PagingSource<Int,Media>() {

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { anchor ->
            val anchorPage = state.closestPageToPosition(anchor)
            (anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1))
        }
    }
}