package com.feature.search.searchUi.pagging


import androidx.paging.PagingState
import com.domain.search.useCases.GetMediaByActorNameUseCase
import com.domain.search.useCases.SortingMediaByCategoriesInteractionUseCase
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.screen.search.MediaUiState

class FindByActorPagingSource(
    actorName: String,
    private val getMediaByActorNameUseCase: GetMediaByActorNameUseCase,
    private val sortingMediaByCategoriesInteractionUseCase: SortingMediaByCategoriesInteractionUseCase

) : BasePagingSource<MediaUiState>(
    searchUseCase = { query, page ->
        sortingMediaByCategoriesInteractionUseCase(getMediaByActorNameUseCase(query,page)).toMediaUiList()
    },
    query = actorName
) {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaUiState> {
        return super.load(params)
    }

    override fun getRefreshKey(state: PagingState<Int, MediaUiState>): Int? {
        return super.getRefreshKey(state)
    }


}