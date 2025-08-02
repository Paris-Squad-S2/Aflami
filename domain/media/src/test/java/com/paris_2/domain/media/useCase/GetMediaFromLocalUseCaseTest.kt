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

class GetMediaFromLocalUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = GetMediaFromLocalUseCase(mediaRepository)

    private val fakeMediaList = listOf(
        Media(
            id = 1,
            title = "Action",
            rating = 8.1,
            imageUri = "a.jpg",
            yearOfRelease = LocalDate(2022, 2, 2),
            categoryIds = listOf(28),
            type = MediaType.MOVIE
        ),
        Media(
            id = 2,
            title = "Drama",
            rating = 7.7,
            imageUri = "b.jpg",
            yearOfRelease = LocalDate(2021, 5, 7),
            categoryIds = listOf(18),
            type = MediaType.TVSHOW
        )
    )

    @Test
    fun `invoke returns local media list from repository`() = runTest {
        coEvery { mediaRepository.getMediaFromLocal() } returns fakeMediaList
        val result = useCase()
        assertThat(result).isEqualTo(fakeMediaList)
    }
}
