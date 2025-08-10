package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.entity.Season
import com.paris_2.domain.media.repository.TvShowRepository
import javax.inject.Inject

class GetSeasonDetailsUseCase @Inject constructor(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int,seasonNumber: Int): Season{
        return tvShowRepository.getSeasonDetails(tvShowId,seasonNumber)
    }
}