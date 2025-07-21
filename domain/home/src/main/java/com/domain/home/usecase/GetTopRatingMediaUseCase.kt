package com.domain.home.usecase

import com.domain.home.model.TopRatingMedia
import com.domain.home.repository.MediaRepository

class GetTopRatingMediaUseCase(
    private val mediaRepository : MediaRepository
) {
    suspend operator fun invoke() : List<TopRatingMedia>{
        return mediaRepository.getTopRatingMedia()
    }
}