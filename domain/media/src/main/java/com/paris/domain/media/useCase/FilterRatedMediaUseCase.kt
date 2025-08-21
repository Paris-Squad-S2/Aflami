package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.repository.MediaRepository

class FilterRatedMediaUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(accountId: Int,mediaType: MediaType): List<Media> {
        return mediaRepository.getRatedMedia(accountId).filter { it.type == mediaType }
    }
}
