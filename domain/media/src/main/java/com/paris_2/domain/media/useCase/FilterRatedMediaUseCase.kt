package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository
import javax.inject.Inject

class FilterRatedMediaUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(accountId: Int,mediaType: MediaType): List<Media> {
        return mediaRepository.getRatedMedia(accountId).filter { it.type == mediaType }
    }
}
