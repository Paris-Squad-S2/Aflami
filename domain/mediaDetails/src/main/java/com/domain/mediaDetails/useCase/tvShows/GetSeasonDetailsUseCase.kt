package com.domain.mediaDetails.useCase.tvShows

import com.domain.media.entity.Season
import com.domain.media.repository.TvShowRepository

class GetSeasonDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int,seasonNumber: Int): Season{
        return tvShowRepository.getSeasonDetails(tvShowId,seasonNumber)
    }
}