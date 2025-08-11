package com.paris_2.domain.media.useCase

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.MediaRepository
import com.paris_2.domain.media.testUtils.createMedia
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test


class GetMoviesByCategoryUseCaseTest {

    private lateinit var mediaRepository: MediaRepository
    private lateinit var getMoviesByCategoryUseCase: GetMoviesByCategoryUseCase

    @BeforeEach
    fun setUp() {
        mediaRepository = mockk()
        getMoviesByCategoryUseCase = GetMoviesByCategoryUseCase(mediaRepository)
    }

    @Test
    fun `should return only movies for given category`() = runTest {
        // Given
        coEvery { mediaRepository.getMoviesByCategory(genreId, page) } returns moviesList

        // When
        val result = getMoviesByCategoryUseCase(genreId, page)

        // Then
        assertThat(result).hasSize(3)
    }

    @Test
    fun `should return correct movie titles for given category`() = runTest {
        // Given
        coEvery { mediaRepository.getMoviesByCategory(genreId, page) } returns moviesList

        // When
        val result = getMoviesByCategoryUseCase(genreId, page)

        // Then
        assertThat(result.map { it.title }).containsExactly("Movie 1", "Movie 2", "Movie 3")
    }

    @Test
    fun `should verify repository is called exactly once`() = runTest {
        // Given
        coEvery { mediaRepository.getMoviesByCategory(genreId, page) } returns moviesList

        // When
        getMoviesByCategoryUseCase(genreId, page)

        // Then
        coVerify(exactly = 1) { mediaRepository.getMoviesByCategory(genreId, page) }
    }

    @Test
    fun `should return empty list when repository returns no movies`() = runTest {
        // Given
        coEvery { mediaRepository.getMoviesByCategory(genreId, page) } returns emptyList()

        // When
        val result = getMoviesByCategoryUseCase(genreId, page)

        // Then
        assertThat(result).isEmpty()
    }

    private companion object {
        val genreId = 28
        val page = 1
        val moviesList = listOf(
            createMedia(id = 1, title = "Movie 1", type = MediaType.Movie),
            createMedia(id = 2, title = "Movie 2", type = MediaType.Movie),
            createMedia(id = 3, title = "Movie 3", type = MediaType.Movie)
        )
    }
}