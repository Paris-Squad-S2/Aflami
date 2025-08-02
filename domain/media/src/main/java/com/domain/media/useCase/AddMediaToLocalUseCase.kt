package com.domain.media.useCase

import com.domain.media.entity.Media
import com.domain.media.repository.MediaRepository

class AddMediaToLocalUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(media: Media) {
        mediaRepository.addMediaToContinueWatching(media = media)
    }
}