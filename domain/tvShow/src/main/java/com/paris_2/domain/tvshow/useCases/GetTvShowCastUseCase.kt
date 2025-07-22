package com.paris_2.domain.tvshow.useCases

import com.paris_2.domain.tvshow.model.TvShowCast
import com.paris_2.domain.tvshow.repository.TvShowRepository

class GetTvShowCastUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<TvShowCast>{
        return tvShowRepository.getTvShowCast(tvShowId)
    }
}