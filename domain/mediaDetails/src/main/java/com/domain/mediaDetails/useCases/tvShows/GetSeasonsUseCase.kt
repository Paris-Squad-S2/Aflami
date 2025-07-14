package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.model.Season
import com.domain.mediaDetails.repository.TvShowRepository

class GetSeasonsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Season>{
        return tvShowRepository.getTvShowSeasons(tvShowId)
    }
}