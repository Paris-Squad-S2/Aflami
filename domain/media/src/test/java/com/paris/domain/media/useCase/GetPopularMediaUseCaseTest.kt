package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.repository.MediaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category

class GetPopularMediaUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetPopularMediaUseCase(mediaRepository)

    @Test
    fun `invoke returns popular media from repository`() = runTest {
        coEvery { mediaRepository.getPopularMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Popular One",
            rating = 8.4,
            imageUri = "pop1.jpg",
            yearOfRelease = LocalDate(2023, 6, 6),
            categories = listOf(Category.Drama),
            type = MediaType.Movie
        ),
        Media(
            id = 2,
            title = "Popular Two",
            rating = 7.9,
            imageUri = "pop2.jpg",
            yearOfRelease = LocalDate(2022, 11, 11),
            categories = listOf(Category.Family),
            type = MediaType.TvShow
        )
    )
}
