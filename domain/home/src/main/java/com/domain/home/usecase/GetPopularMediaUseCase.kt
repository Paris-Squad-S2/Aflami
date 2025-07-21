package com.domain.home.usecase

import com.domain.home.model.PopularMedia
import com.domain.home.repository.MediaRepository

class GetPopularMediaUseCase(
    private val mediaRepository : MediaRepository
) {
    suspend operator fun invoke() : List<PopularMedia> {
        return mediaRepository.getPopularMedia()
    }
}