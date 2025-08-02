package com.domain.media.useCase.tvShows

import com.domain.media.repository.TvShowRepository

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