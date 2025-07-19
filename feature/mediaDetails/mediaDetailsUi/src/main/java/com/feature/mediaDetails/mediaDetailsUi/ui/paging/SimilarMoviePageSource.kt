package com.feature.mediaDetails.mediaDetailsUi.ui.paging

import androidx.paging.PagingState
import com.domain.mediaDetails.useCases.movie.GetMovieRecommendationsUseCase
import com.feature.mediaDetails.mediaDetailsUi.ui.mapper.toListOfMovieSimilarUI
import com.feature.mediaDetails.mediaDetailsUi.ui.pagging.BasePagingSource
import com.feature.mediaDetails.mediaDetailsUi.ui.screen.SimilarMediaUI

class SimilarMoviePageSource(
    movieId: Int,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase
) : BasePagingSource<SimilarMediaUI>(
    itemId = movieId,
    mediaUseCase = { itemId, page ->
        getMovieRecommendationsUseCase(itemId, page).toListOfMovieSimilarUI()
    }
) {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SimilarMediaUI> {
        return super.load(params)
    }

    override fun getRefreshKey(state: PagingState<Int, SimilarMediaUI>): Int? {
        return super.getRefreshKey(state)
    }
}