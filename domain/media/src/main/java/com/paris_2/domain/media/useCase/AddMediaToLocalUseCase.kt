package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.MediaRepository

class AddMediaToLocalUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(media: Media) {
        mediaRepository.addMediaToContinueWatching(media = media)
    }
}