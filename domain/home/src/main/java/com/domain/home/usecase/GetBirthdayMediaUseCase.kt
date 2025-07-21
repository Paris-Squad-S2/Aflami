package com.domain.home.usecase

import com.domain.home.model.Media
import com.domain.home.repository.MediaRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class GetBirthdayMediaUseCase(
    private val mediaRepository : MediaRepository
) {
    @OptIn(ExperimentalTime::class)
    suspend operator fun invoke(): List<Media> {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return mediaRepository.getPopularMedia().filter { media ->
            media.yearOfRelease.month == today.month &&
                    media.yearOfRelease.day == today.day
        }
    }
}
