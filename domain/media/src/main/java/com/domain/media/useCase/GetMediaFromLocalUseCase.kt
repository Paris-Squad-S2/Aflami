package com.domain.media.useCase

import com.domain.media.entity.Media
import com.domain.media.repository.MediaRepository

class GetMediaFromLocalUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(): List<Media> {
        return mediaRepository.getMediaFromLocal()
    }
}