package com.paris_2.domain.tvshow.useCases

import com.paris_2.domain.tvshow.model.Season
import com.paris_2.domain.tvshow.repository.TvShowRepository

class GetSeasonDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int,seasonNumber: Int): Season{
        return tvShowRepository.getSeasonDetails(tvShowId,seasonNumber)
    }
}