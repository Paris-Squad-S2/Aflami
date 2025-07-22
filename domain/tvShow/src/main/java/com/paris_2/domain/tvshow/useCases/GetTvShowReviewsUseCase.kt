package com.paris_2.domain.tvshow.useCases

import com.paris_2.domain.tvshow.model.TvShowReview
import com.paris_2.domain.tvshow.repository.TvShowRepository

class GetTvShowReviewsUseCase(
    private val tbShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<TvShowReview> {
        return tbShowRepository.getTvShowReview(tvShowId, page)
    }

}