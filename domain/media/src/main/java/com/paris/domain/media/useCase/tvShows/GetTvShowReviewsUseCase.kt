package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.entity.Review
import com.paris.domain.media.repository.TvShowRepository

class GetTvShowReviewsUseCase(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<Review> {
        return tvShowRepository.getTvShowReview(tvShowId, page)
    }
}