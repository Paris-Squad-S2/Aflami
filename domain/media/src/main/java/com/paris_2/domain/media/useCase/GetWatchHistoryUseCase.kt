package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.MediaRepository
import kotlinx.coroutines.flow.Flow

class GetWatchHistoryUseCase(
    private val mediaRepository: MediaRepository
) {
    operator fun invoke(): Flow<List<Media>> {
        return mediaRepository.getContinueWatchingMedia()
    }
}