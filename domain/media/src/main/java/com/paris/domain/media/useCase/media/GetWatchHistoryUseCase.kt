package com.paris.domain.media.useCase.media

import com.paris.domain.media.entity.Media
import com.paris.domain.media.repository.MediaRepository
import kotlinx.coroutines.flow.Flow

class GetWatchHistoryUseCase(
    private val mediaRepository: MediaRepository
) {
    operator fun invoke(): Flow<List<Media>> {
        return mediaRepository.getContinueWatchingMedia()
    }
}