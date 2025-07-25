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

class FilterUpComingMediaByCategoriesUseCaseTest {
    private val mediaRepository: MediaRepository = mockk()
    private val useCase = FilterUpComingMediaByCategoriesUseCase(mediaRepository)

    private val actionMedia = Media(
        id = 1,
        title = "Action Movie",
        voteAverage = 7.5,
        posterPath = "path1.jpg",
        yearOfRelease = LocalDate(2023, 5, 1),
        genreIds = listOf(28),
        type = MediaType.MOVIE
    )

    private val comedyMedia = Media(
        id = 2,
        title = "Comedy Movie",
        voteAverage = 7.2,
        posterPath = "path2.jpg",
        yearOfRelease = LocalDate(2022, 10, 10),
        genreIds = listOf(35),
        type = MediaType.MOVIE
    )

    @Test
    fun `invoke filters media by categories correctly`() = runTest {
        coEvery { mediaRepository.getUpComingMedia() } returns listOf(actionMedia, comedyMedia)
        val result = useCase(listOf(28))
        assertThat(result).containsExactly(actionMedia)
        val resultAll = useCase(listOf(28, 35))
        assertThat(resultAll).containsExactly(actionMedia, comedyMedia)
        val resultNone = useCase(listOf(18))
        assertThat(resultNone).isEmpty()
    }
}
