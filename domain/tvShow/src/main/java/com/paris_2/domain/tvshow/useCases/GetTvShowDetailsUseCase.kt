package com.paris_2.domain.tvshow.useCases

import com.paris_2.domain.tvshow.model.TvShow
import com.paris_2.domain.tvshow.repository.TvShowRepository

class GetTvShowDetailsUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): TvShow {
        return tvShowRepository.getTvShowDetails(tvShowId)
    }
}