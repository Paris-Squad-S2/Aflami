package com.domain.mediaDetails.useCase.movie

import com.domain.mediaDetails.repository.MovieRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddRatingToMovieUseCaseTest {

    private lateinit var repository: MovieRepository
    private lateinit var useCase: AddRatingToMovieUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        useCase = AddRatingToMovieUseCase(repository)
    }

    @Test
    fun `invoke should call addRatingToMovie on repository`() = runTest {
        // Arrange
        val movieId = 123
        val rating = 8.5f
        val sessionId = "dummy_session_id"

        // Act
        useCase(movieId, rating, sessionId)

        // Assert
        coVerify(exactly = 1) {
            repository.addRatingToMovie(movieId, rating, sessionId)
        }
    }
}