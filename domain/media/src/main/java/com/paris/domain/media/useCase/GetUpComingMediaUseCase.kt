package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Media
import com.paris.domain.media.repository.MediaRepository

class GetUpComingMediaUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(): List<Media> {
        return mediaRepository.getUpComingMedia()
    }
}