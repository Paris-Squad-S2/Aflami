package com.feature.mediaDetails.mediaDetailsUi.ui.paging

import androidx.paging.PagingState
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfMTvShowSimilarUI
import com.feature.mediaDetails.mediaDetailsUi.ui.pagging.BasePagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI
import com.paris_2.domain.tvshow.useCases.GetTvShowRecommendationsUseCase

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