package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import testUtils.fakeCast

class GetTvShowCastUseCaseTest {

    private lateinit var getTvShowCastUseCase: GetTvShowCastUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getTvShowCastUseCase = GetTvShowCastUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show cast from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns fakeCast

        // When
        val result = getTvShowCastUseCase(tvShowId)

        // Then
        assertEquals(fakeCast, result)
    }

    @Test
    fun `should call repository method to get cast`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns fakeCast

        // When
        getTvShowCastUseCase(tvShowId)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowCast(tvShowId) }
    }

    @Test
    fun `should return empty list when no cast found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns emptyList()

        // When
        val result = getTvShowCastUseCase(tvShowId)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `should call repository method even if no cast found`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowCast(tvShowId) } returns emptyList()

        // When
        getTvShowCastUseCase(tvShowId)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowCast(tvShowId) }
    }

    private companion object {
        const val tvShowId = 1
    }
}
