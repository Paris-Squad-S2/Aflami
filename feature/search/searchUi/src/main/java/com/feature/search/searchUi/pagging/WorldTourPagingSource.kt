package com.feature.search.searchUi.pagging

import androidx.paging.PagingState
import com.domain.search.useCases.GetMoviesOnlyByCountryNameUseCase
import com.feature.search.searchUi.mapper.toMediaUiList
import com.feature.search.searchUi.screen.search.MediaUiState

class WorldTourPagingSource(
    private val countryName: String,
    private val getMoviesByCountryUseCase: GetMoviesOnlyByCountryNameUseCase,
): DefaultPagingSource<MediaUiState>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaUiState> {
        val page = params.key ?: 1
            return try {
            val response = getMoviesByCountryUseCase(countryName = countryName,page = page).toMediaUiList()
                LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int,MediaUiState>): Int? {
        return super.getRefreshKey(state)
    }

}