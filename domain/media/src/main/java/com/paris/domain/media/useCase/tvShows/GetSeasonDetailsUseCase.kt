package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.entity.Season
import com.paris.domain.media.repository.TvShowRepository

class GetSeasonDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int,seasonNumber: Int): Season{
        return tvShowRepository.getSeasonDetails(tvShowId,seasonNumber)
    }
}