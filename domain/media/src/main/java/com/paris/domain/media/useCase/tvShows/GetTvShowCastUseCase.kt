package com.paris.domain.media.useCase.tvShows

import com.paris.domain.media.entity.Cast
import com.paris.domain.media.repository.TvShowRepository

class GetTvShowCastUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Cast>{
        return tvShowRepository.getTvShowCast(tvShowId)
    }
}