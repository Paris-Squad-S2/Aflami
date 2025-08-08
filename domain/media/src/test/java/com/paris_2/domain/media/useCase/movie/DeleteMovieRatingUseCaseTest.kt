package com.paris_2.domain.media.useCase.movie

import com.paris_2.domain.media.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class DeleteMovieRatingUseCaseTest {
    private lateinit var deleteMovieRatingUseCase: DeleteMovieRatingUseCase
    private val movieRepository: MovieRepository = mockk(relaxed = true)

    @BeforeTest
    fun setup() {
        deleteMovieRatingUseCase = DeleteMovieRatingUseCase(movieRepository)
    }

    @Test
    fun `should call repository to delete movie rating`() = runTest {
        // Given
        coEvery { movieRepository.deleteMovieRating(movieId) } returns Unit

        // When
        deleteMovieRatingUseCase(movieId)

        // Then
        coVerify(exactly = 1) { movieRepository.deleteMovieRating(movieId) }
    }

    @Test
    fun `should throw exception when repository throws`() = runTest {
        // Given
        val exception = RuntimeException("Failed to delete rating")
        coEvery { movieRepository.deleteMovieRating(movieId) } throws exception

        // Then
        val thrown = kotlin.runCatching {
            deleteMovieRatingUseCase(movieId)
        }.exceptionOrNull()

        kotlin.test.assertEquals(exception, thrown)
    }

    private companion object {
        const val movieId = 123
    }

}