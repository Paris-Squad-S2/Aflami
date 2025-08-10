package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.entity.Review
import com.paris_2.domain.media.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowReviewsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository,
) {
    suspend operator fun invoke(tvShowId: Int, page: Int): List<Review> {
        return tvShowRepository.getTvShowReview(tvShowId, page)
    }
}