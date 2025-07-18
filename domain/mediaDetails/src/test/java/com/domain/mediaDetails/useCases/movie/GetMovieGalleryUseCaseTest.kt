package com.domain.mediaDetails.useCases.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coEvery
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
        val movieId = 1

        // when
        coEvery { movieRepository.getMovieGallery(movieId) } returns fakeGallery

        // Then
        val result = getMovieGalleryUseCase(movieId)
        assertEquals(result, fakeGallery)

    }

}