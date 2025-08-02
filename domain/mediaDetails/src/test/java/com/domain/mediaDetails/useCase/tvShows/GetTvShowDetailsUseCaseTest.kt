package com.domain.mediaDetails.useCase.tvShows

import com.domain.media.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import testUtils.fakeTvShow

class GetTvShowDetailsUseCaseTest {

    private lateinit var getTvShowDetailsUseCase: GetTvShowDetailsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getTvShowDetailsUseCase = GetTvShowDetailsUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show details from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowDetails(tvShowId) } returns fakeTvShow

        // When
        val result = getTvShowDetailsUseCase(tvShowId)

        // Then
        assertEquals(fakeTvShow, result)
    }

    @Test
    fun `should call repository method exactly once`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowDetails(tvShowId) } returns fakeTvShow

        // When
        getTvShowDetailsUseCase(tvShowId)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowDetails(tvShowId) }
    }

    private companion object {
        const val tvShowId = 1
    }
}
