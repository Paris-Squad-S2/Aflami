package com.domain.media.useCase

import com.domain.media.entity.Media
import com.domain.media.entity.MediaType
import com.domain.media.repository.MediaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat

class GetUpComingMediaUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetUpComingMediaUseCase(mediaRepository)

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Upcoming One",
            rating = 8.6,
            imageUri = "up1.jpg",
            yearOfRelease = LocalDate(2023, 12, 12),
            categoryIds = listOf(28),
            type = MediaType.MOVIE
        ),
        Media(
            id = 2,
            title = "Upcoming Two",
            rating = 7.8,
            imageUri = "up2.jpg",
            yearOfRelease = LocalDate(2024, 2, 2),
            categoryIds = listOf(18),
            type = MediaType.TVSHOW
        )
    )

    @Test
    fun `invoke returns upcoming media from repository`() = runTest {
        coEvery { mediaRepository.getUpComingMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }
}
