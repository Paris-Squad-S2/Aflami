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

class GetPopularMediaUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetPopularMediaUseCase(mediaRepository)

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Popular One",
            rating = 8.4,
            imageUri = "pop1.jpg",
            yearOfRelease = LocalDate(2023, 6, 6),
            categoryIds = listOf(35),
            type = MediaType.MOVIE
        ),
        Media(
            id = 2,
            title = "Popular Two",
            rating = 7.9,
            imageUri = "pop2.jpg",
            yearOfRelease = LocalDate(2022, 11, 11),
            categoryIds = listOf(18),
            type = MediaType.TVSHOW
        )
    )

    @Test
    fun `invoke returns popular media from repository`() = runTest {
        coEvery { mediaRepository.getPopularMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }
}
