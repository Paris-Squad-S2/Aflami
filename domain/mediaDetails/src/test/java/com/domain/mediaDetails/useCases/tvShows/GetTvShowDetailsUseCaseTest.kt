package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeTvShow
import kotlin.test.assertEquals

class GetTvShowDetailsUseCaseTest {

    private lateinit var getTvShowDetailsUseCase: GetTvShowDetailsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getTvShowDetailsUseCase = GetTvShowDetailsUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show details from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowDetails(tvShowId) } returns fakeTvShow

        // when
        val result = getTvShowDetailsUseCase(tvShowId)

        // Then
        assertEquals(result, fakeTvShow)

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
        val tvShowId = 1

    }

}