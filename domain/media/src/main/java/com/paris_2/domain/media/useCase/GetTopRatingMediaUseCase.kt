package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.MediaRepository
import javax.inject.Inject

class GetTopRatingMediaUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(): List<Media> {
        return mediaRepository.getTopRatingMedia()
    }
}