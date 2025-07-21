package com.domain.home.usecase

import com.domain.home.model.ContinueWatchingMedia
import com.domain.home.repository.MediaRepository

class GetContinueWatchingMediaUseCase(
    private val mediaRepository : MediaRepository
) {
    suspend operator fun invoke() : List<ContinueWatchingMedia> {
        return mediaRepository.getContinueWatchingMedia()
    }
}