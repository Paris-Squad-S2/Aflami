package com.domain.mediaDetails.useCase.tvShows

import com.domain.media.entity.Cast
import com.domain.media.repository.TvShowRepository

class GetTvShowCastUseCase(
    private val tvShowRepository: TvShowRepository
) {
    suspend operator fun invoke(tvShowId: Int): List<Cast>{
        return tvShowRepository.getTvShowCast(tvShowId)
    }
}