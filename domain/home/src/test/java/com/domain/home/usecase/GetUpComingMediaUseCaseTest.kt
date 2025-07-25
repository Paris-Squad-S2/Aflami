package com.domain.home.usecase

import com.domain.home.model.Media
import com.domain.home.model.MediaType
import com.domain.home.repository.MediaRepository
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
            voteAverage = 8.6,
            posterPath = "up1.jpg",
            yearOfRelease = LocalDate(2023, 12, 12),
            genreIds = listOf(28),
            type = MediaType.MOVIE
        ),
        Media(
            id = 2,
            title = "Upcoming Two",
            voteAverage = 7.8,
            posterPath = "up2.jpg",
            yearOfRelease = LocalDate(2024, 2, 2),
            genreIds = listOf(18),
            type = MediaType.TV_SHOW
        )
    )

    @Test
    fun `invoke returns upcoming media from repository`() = runTest {
        coEvery { mediaRepository.getUpComingMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }
}
