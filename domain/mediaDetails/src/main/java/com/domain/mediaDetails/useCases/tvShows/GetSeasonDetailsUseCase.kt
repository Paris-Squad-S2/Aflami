package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.repository.TvShowRepository

class GetSeasonDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int,seasonNumber: Int): Season{
        return tvShowRepository.getSeasonDetails(tvShowId,seasonNumber)
    }
}