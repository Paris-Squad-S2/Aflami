package com.domain.home.usecase

import com.domain.home.model.Media
import com.domain.home.repository.MediaRepository

class GetMediaFromLocalUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(): List<Media>{
        return mediaRepository.getMediaFromLocal()
    }
}