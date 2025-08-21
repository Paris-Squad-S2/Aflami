package com.paris.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.repository.MediaRepository
import com.paris.domain.media.useCase.media.GetUpComingMediaUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test

class GetUpComingMediaUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetUpComingMediaUseCase(mediaRepository)

    @Test
    fun `invoke returns upcoming media from repository`() = runTest {
        coEvery { mediaRepository.getUpComingMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Upcoming One",
            rating = 8.6,
            imageUri = "up1.jpg",
            yearOfRelease = LocalDate(2023, 12, 12),
            categories = listOf(Category.Action),
            type = MediaType.Movie
        ),
        Media(
            id = 2,
            title = "Upcoming Two",
            rating = 7.8,
            imageUri = "up2.jpg",
            yearOfRelease = LocalDate(2024, 2, 2),
            categories = listOf(Category.Adventure),
            type = MediaType.TvShow
        )
    )
}
