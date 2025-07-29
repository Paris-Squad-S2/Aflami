package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.repository.TvShowRepository

class AddRatingToTvShowUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(movieId: Int, rating: Float) {
        return tvShowRepository.addRatingToTvShow(
            movieId,
            rating
        )
    }
}