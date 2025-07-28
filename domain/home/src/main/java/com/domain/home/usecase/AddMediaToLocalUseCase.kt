package com.domain.home.usecase

import com.domain.home.model.Media
import com.domain.home.repository.MediaRepository

class AddMediaToLocalUseCase(
    private val mediaRepository: MediaRepository
) {
    suspend operator fun invoke(media: Media){
        mediaRepository.addMediaToCountineWatch(media = media)
    }
}