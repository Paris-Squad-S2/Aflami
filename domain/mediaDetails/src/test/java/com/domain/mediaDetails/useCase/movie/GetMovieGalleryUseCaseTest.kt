package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeGallery
import kotlin.test.assertEquals

class GetMovieGalleryUseCaseTest {
    private lateinit var getMovieGalleryUseCase: GetMovieGalleryUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getMovieGalleryUseCase = GetMovieGalleryUseCase(movieRepository)
    }

    @Test
    fun `should return movie gallery from repository`() = runTest {
        // Given
        coEvery { movieRepository.getMovieGallery(movieId) } returns fakeGallery

        // when
        val result = getMovieGalleryUseCase(movieId)

        // Then
        assertEquals(result, fakeGallery)

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

    private companion object{
        val movieId = 1

    }
}