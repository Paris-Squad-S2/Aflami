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

class GetPopularMediaUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetPopularMediaUseCase(mediaRepository)

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Popular One",
            voteAverage = 8.4,
            posterPath = "pop1.jpg",
            yearOfRelease = LocalDate(2023, 6, 6),
            genreIds = listOf(35),
            type = MediaType.MOVIE
        ),
        Media(
            id = 2,
            title = "Popular Two",
            voteAverage = 7.9,
            posterPath = "pop2.jpg",
            yearOfRelease = LocalDate(2022, 11, 11),
            genreIds = listOf(18),
            type = MediaType.TV_SHOW
        )
    )

    @Test
    fun `invoke returns popular media from repository`() = runTest {
        coEvery { mediaRepository.getPopularMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }
}
