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

class GetMediaFromLocalUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetMediaFromLocalUseCase(mediaRepository)

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Action",
            voteAverage = 8.1,
            posterPath = "a.jpg",
            yearOfRelease = LocalDate(2022, 2, 2),
            genreIds = listOf(28),
            type = MediaType.MOVIE
        ),
        Media(
            id = 2,
            title = "Drama",
            voteAverage = 7.7,
            posterPath = "b.jpg",
            yearOfRelease = LocalDate(2021, 5, 7),
            genreIds = listOf(18),
            type = MediaType.TV_SHOW
        )
    )

    @Test
    fun `invoke returns local media list from repository`() = runTest {
        coEvery { mediaRepository.getMediaFromLocal() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }
}
