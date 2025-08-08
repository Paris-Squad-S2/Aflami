package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.repository.TvShowRepository

class DeleteTvShowRatingUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int){
        return tvShowRepository.deleteTvShowRating(tvShowId)
    }
}