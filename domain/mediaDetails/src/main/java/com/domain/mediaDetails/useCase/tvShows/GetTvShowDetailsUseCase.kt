package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.entity.TvShow
import com.domain.mediaDetails.repository.TvShowRepository

class GetTvShowDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): TvShow {
        return tvShowRepository.getTvShowDetails(tvShowId)
    }
}