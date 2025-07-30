package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowReviewsUseCase(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<Review> {
        return tvShowRepository.getTvShowReview(tvShowId, page)
    }
}