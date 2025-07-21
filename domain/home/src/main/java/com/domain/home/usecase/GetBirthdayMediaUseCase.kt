package com.domain.home.usecase

import com.domain.home.model.BirthdayMedia
import com.domain.home.repository.MediaRepository

class GetBirthdayMediaUseCase(
    private val mediaRepository : MediaRepository
) {
    suspend operator fun invoke(): List<BirthdayMedia>{
        return mediaRepository.getBirthdayMedia()
    }
}