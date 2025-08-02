package com.domain.media.useCase.tvShows

import com.domain.media.entity.TvShow
import com.domain.media.repository.TvShowRepository

class GetTvShowDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): TvShow {
        return tvShowRepository.getTvShowDetails(tvShowId)
    }
}