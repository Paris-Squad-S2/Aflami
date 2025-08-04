package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.paris_2.domain.media.testUtils.fakeGallery
import kotlin.test.assertEquals

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
        assertEquals(fakeGallery, result)
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