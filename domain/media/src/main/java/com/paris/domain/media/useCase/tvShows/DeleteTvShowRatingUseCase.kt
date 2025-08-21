package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.repository.TvShowRepository

class DeleteTvShowRatingUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int){
        return tvShowRepository.deleteTvShowRating(tvShowId)
    }
}