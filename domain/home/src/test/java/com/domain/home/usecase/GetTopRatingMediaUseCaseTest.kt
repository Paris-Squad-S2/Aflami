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

class GetTopRatingMediaUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetTopRatingMediaUseCase(mediaRepository)

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Top Rated One",
            voteAverage = 9.1,
            posterPath = "top1.jpg",
            yearOfRelease = LocalDate(2021, 4, 4),
            genreIds = listOf(18),
            type = MediaType.MOVIE
        ),
        Media(
            id = 2,
            title = "Top Rated Two",
            voteAverage = 8.9,
            posterPath = "top2.jpg",
            yearOfRelease = LocalDate(2020, 7, 7),
            genreIds = listOf(35),
            type = MediaType.TV_SHOW
        )
    )

    @Test
    fun `invoke returns top-rated media from repository`() = runTest {
        coEvery { mediaRepository.getTopRatingMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }
}
