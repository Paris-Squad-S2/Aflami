package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.model.TvShow
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): TvShow {
        return tvShowRepository.getTvShowDetails(tvShowId)
    }
}