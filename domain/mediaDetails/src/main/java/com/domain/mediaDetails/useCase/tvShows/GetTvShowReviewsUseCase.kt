package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.model.Review
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowReviewsUseCase(
    private val tbShowRepository: TvShowRepository,
) {


    suspend operator fun invoke(tvShowId: Int, page: Int = 1): List<Review> {
        return tbShowRepository.getTvShowReview(tvShowId, page)
    }


}