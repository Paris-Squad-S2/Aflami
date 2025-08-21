package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.entity.TvShow
import com.paris.domain.media.repository.TvShowRepository

class GetTvShowDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): TvShow {
        return tvShowRepository.getTvShowDetails(tvShowId)
    }
}