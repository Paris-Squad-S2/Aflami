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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetWatchHistoryUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetWatchHistoryUseCase(mediaRepository)

    @Test
    fun `invoke returns local media list from repository`() = runTest {
        coEvery { mediaRepository.getContinueWatchingMedia() } returns flowOf(fakeMediaList)
        val result = useCase()
        assertThat(result.first()).isEqualTo(fakeMediaList)
    }

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Action",
            rating = 8.1,
            imageUri = "a.jpg",
            yearOfRelease = LocalDate(2022, 2, 2),
            categories = listOf(Category.Action),
            type = MediaType.Movie
        ),
        Media(
            id = 2,
            title = "Drama",
            rating = 7.7,
            imageUri = "b.jpg",
            yearOfRelease = LocalDate(2021, 5, 7),
            categories = listOf(Category.Drama),
            type = MediaType.TvShow
        )
    )
}
