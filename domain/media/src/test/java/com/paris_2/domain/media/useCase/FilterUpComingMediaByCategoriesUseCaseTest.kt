package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.entity.Category

class FilterUpComingMediaByCategoriesUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = FilterUpComingMediaByCategoriesUseCase(mediaRepository)

    @Test
    fun `invoke filters media by categories correctly`() = runTest {
        coEvery { mediaRepository.getUpComingMedia() } returns listOf(actionMedia, comedyMedia)
        val result = useCase(listOf(Category.Action))
        assertThat(result).containsExactly(actionMedia)
        val resultAll = useCase(listOf(Category.Action, Category.Adventure))
        assertThat(resultAll).containsExactly(actionMedia, comedyMedia)
        val resultNone = useCase(listOf(Category.Drama))
        assertThat(resultNone).isEmpty()
    }

    private val actionMedia = Media(
        id = 1,
        title = "Action Movie",
        rating = 7.5,
        imageUri = "path1.jpg",
        yearOfRelease = LocalDate(2023, 5, 1),
        categories = listOf(Category.Action),
        type = MediaType.Movie
    )

    private val comedyMedia = Media(
        id = 2,
        title = "Comedy Movie",
        rating = 7.2,
        imageUri = "path2.jpg",
        yearOfRelease = LocalDate(2022, 10, 10),
        categories = listOf(Category.Adventure),
        type = MediaType.Movie
    )
}
