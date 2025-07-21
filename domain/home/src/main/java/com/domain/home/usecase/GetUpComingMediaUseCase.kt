package com.domain.home.usecase

import com.domain.home.model.UpComingMedia
import com.domain.home.repository.MediaRepository

class GetUpComingMediaUseCase(
    private val mediaRepository : MediaRepository
) {
    suspend operator fun invoke() : List<UpComingMedia>{
        return mediaRepository.getUpComingMedia()
    }
}