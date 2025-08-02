package com.domain.mediaDetails.useCase.tvShows

import com.domain.media.entity.Review
import com.domain.media.repository.TvShowRepository

class GetTvShowReviewsUseCase(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<Review> {
        return tvShowRepository.getTvShowReview(tvShowId, page)
    }
}