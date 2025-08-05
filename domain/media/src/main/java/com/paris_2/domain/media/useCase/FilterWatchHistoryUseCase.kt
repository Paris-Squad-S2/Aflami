package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository

class FilterWatchHistoryUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(mediaType: MediaType): List<Media> {
        return mediaRepository.getMediaFromLocal().filter { it.type == mediaType }
    }
}