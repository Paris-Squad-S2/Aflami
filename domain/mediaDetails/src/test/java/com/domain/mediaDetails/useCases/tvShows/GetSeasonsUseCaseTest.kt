package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import testUtils.fakeSeasons
import kotlin.test.assertEquals

class GetSeasonsUseCaseTest {

    private lateinit var getSeasonsUseCase: GetSeasonsUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getSeasonsUseCase = GetSeasonsUseCase(tvShowRepository)
    }

    @Test
    fun `should return seasons from repository`() = runTest {
        // Given
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getTvShowSeasons(tvShowId) } returns fakeSeasons

        // Then
        val result = getSeasonsUseCase(tvShowId)
        assertEquals(result, fakeSeasons)

    }

    @Test
    fun `should return empty list when no cast found`() = runTest{
        // Given
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getTvShowSeasons(tvShowId) } returns emptyList()

        // Then
        val result = getSeasonsUseCase(tvShowId)
        assertEquals(result, emptyList())
    }

}