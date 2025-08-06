package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository

class FilterRatedMediaUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(accountId: Int,sessionId: String,mediaType: MediaType): List<Media> {
        return mediaRepository.getRatedMedia(accountId, sessionId).filter { it.type == mediaType }
    }
}
