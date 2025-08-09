package com.paris_2.domain.media.useCase.tvShows

import com.paris_2.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class DeleteTvShowRatingUseCaseTest {

    private lateinit var deleteTvShowRatingUseCase: DeleteTvShowRatingUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        deleteTvShowRatingUseCase = DeleteTvShowRatingUseCase(tvShowRepository)
    }

    @Test
    fun `should call repository to delete tv show rating`() = runTest {
        // Given
        coEvery { tvShowRepository.deleteTvShowRating(tvShowId) } returns Unit

        // When
        deleteTvShowRatingUseCase(tvShowId)

        // Then
        coVerify(exactly = 1) { tvShowRepository.deleteTvShowRating(tvShowId) }
    }

    @Test
    fun `should throw exception when repository throws`() = runTest {
        // Given
        val exception = RuntimeException("Failed to delete tv show rating")
        coEvery { tvShowRepository.deleteTvShowRating(tvShowId) } throws exception

        // When
        val thrown = runCatching {
            deleteTvShowRatingUseCase(tvShowId)
        }.exceptionOrNull()

        // Then
        assertEquals(exception, thrown)
    }

    private companion object {
        const val tvShowId = 2
    }

}