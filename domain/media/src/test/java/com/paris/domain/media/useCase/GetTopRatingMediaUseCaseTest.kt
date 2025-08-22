package com.paris.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.repository.MediaRepository
import com.paris.domain.media.useCase.media.GetTopRatingMediaUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test

class GetTopRatingMediaUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetTopRatingMediaUseCase(mediaRepository)

    @Test
    fun `invoke returns top-rated media from repository`() = runTest {
        coEvery { mediaRepository.getTopRatingMedia() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Top Rated One",
            rating = 9.1,
            imageUri = "top1.jpg",
            yearOfRelease = LocalDate(2021, 4, 4),
            categories = listOf(Category.Action),
            type = MediaType.Movie
        ),
        Media(
            id = 2,
            title = "Top Rated Two",
            rating = 8.9,
            imageUri = "top2.jpg",
            yearOfRelease = LocalDate(2020, 7, 7),
            categories = listOf(Category.Adventure),
            type = MediaType.TvShow
        )
    )
}
