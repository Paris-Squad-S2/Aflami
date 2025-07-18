package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
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
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getTvShowDetails(tvShowId) } returns fakeTvShow

        // Then
        val result = getTvShowDetailsUseCase(tvShowId)
        assertEquals(result,fakeTvShow)

    }

}