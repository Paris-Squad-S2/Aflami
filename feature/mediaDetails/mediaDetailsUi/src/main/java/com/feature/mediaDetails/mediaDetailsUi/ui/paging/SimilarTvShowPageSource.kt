package com.feature.mediaDetails.mediaDetailsUi.ui.paging

import androidx.paging.PagingState
import com.domain.mediaDetails.useCases.tvShows.GetTvShowRecommendationsUseCase
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfMTvShowSimilarUI
import com.feature.mediaDetails.mediaDetailsUi.ui.pagging.BasePagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI

class SimilarTvShowPageSource(
    movieId: Int,
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase
): BasePagingSource<SimilarMediaUI>(
    itemId = movieId ,
    mediaUseCase = { itemId, page ->
        getTvShowRecommendationsUseCase(itemId,page).toListOfMTvShowSimilarUI()
    }
) {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimilarMediaUI> {
        return super.load(params)
    }

    override fun getRefreshKey(state: PagingState<Int, SimilarMediaUI>): Int? {
        return super.getRefreshKey(state)
    }
}