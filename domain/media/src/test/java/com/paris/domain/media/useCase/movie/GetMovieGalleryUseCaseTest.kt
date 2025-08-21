package com.paris.domain.media.useCase.movie

import com.google.common.truth.Truth.assertThat
import com.paris.domain.media.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris.domain.media.testUtils.fakeGallery

class GetMovieGalleryUseCaseTest {
    private lateinit var getMovieGalleryUseCase: GetMovieGalleryUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getMovieGalleryUseCase = GetMovieGalleryUseCase(movieRepository)
    }

    @Test
    fun `should return movie gallery from repository`() = runTest {
        // Given
        coEvery { movieRepository.getMovieGallery(movieId) } returns fakeGallery

        // When
        val result = getMovieGalleryUseCase(movieId)

        // Then
        assertThat(result).isEqualTo(fakeGallery)
    }

    @Test
    fun `should call repository method when use case is invoked`() = runTest {
        // Given
        coEvery { movieRepository.getMovieGallery(movieId) } returns fakeGallery

        // When
        getMovieGalleryUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.getMovieGallery(movieId) }
    }

    private companion object {
        const val movieId = 1
    }
}