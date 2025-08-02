package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.entity.TvShow
import com.paris_2.domain.media.repository.TvShowRepository

class GetTvShowDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): TvShow {
        return tvShowRepository.getTvShowDetails(tvShowId)
    }
}