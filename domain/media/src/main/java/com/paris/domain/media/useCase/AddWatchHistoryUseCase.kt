package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Media
import com.paris.domain.media.repository.MediaRepository

class AddWatchHistoryUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(media: Media) {
        mediaRepository.addMediaToContinueWatching(media = media)
    }
}