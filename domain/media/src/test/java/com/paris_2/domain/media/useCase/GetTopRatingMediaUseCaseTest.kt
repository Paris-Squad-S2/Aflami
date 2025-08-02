package com.paris_2.domain.media.useCase

import com.domain.media.entity.Media
import com.domain.media.entity.MediaType
import com.domain.media.repository.MediaRepository
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
            rating = 9.1,
            imageUri = "top1.jpg",
            yearOfRelease = LocalDate(2021, 4, 4),
            categoryIds = listOf(18),
            type = MediaType.MOVIE
        ),
        Media(
            id = 2,
            title = "Top Rated Two",
            rating = 8.9,
            imageUri = "top2.jpg",
            yearOfRelease = LocalDate(2020, 7, 7),
            categoryIds = listOf(35),
            type = MediaType.TVSHOW
        )
    )

    @Test
    fun `invoke returns top-rated media from repository`() = runTest {
        coEvery { mediaRepository.getTopRatingMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }
}
