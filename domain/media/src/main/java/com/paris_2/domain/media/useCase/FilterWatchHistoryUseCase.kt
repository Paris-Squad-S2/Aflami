package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository
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