package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.repository.TvShowRepository

class AddRatingToTvShowUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke() {
        return tvShowRepository.addRatingToTvShow()
    }
}