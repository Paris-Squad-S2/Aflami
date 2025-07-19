package com.feature.mediaDetails.mediaDetailsUi.ui.paging

import androidx.paging.PagingState
import com.domain.mediaDetails.useCases.tvShows.GetTvShowReviewsUseCase
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfReviewUi
import com.feature.mediaDetails.mediaDetailsUi.ui.pagging.BasePagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.movie.details.ReviewUi

class ReviewTvShowPagingSource(
    mediaId:Int,
    private val getTvShowReviewsUseCase: GetTvShowReviewsUseCase
): BasePagingSource<ReviewUi>(
    itemId = mediaId,
    mediaUseCase = { tvShowId, page ->
        getTvShowReviewsUseCase(tvShowId ,page).toListOfReviewUi()
    }
) {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewUi> {
        return super.load(params)
    }

    override fun getRefreshKey(state: PagingState<Int, ReviewUi>): Int? {
        return super.getRefreshKey(state)
    }
}