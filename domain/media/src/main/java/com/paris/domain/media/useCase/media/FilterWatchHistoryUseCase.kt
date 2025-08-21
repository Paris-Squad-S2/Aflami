package com.paris.domain.media.useCase.media

import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilterWatchHistoryUseCase(
    private val mediaRepository: MediaRepository
) {
    operator fun invoke(mediaType: MediaType): Flow<List<Media>> {
        return mediaRepository.getContinueWatchingMedia()
            .map { list -> list.filter { it.type == mediaType } }
    }
}