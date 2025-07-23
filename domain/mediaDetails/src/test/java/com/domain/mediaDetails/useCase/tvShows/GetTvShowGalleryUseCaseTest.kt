package com.domain.mediaDetails.useCase.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import testUtils.fakeGallery

class GetTvShowGalleryUseCaseTest {

    private lateinit var getTvShowGalleryUseCase: GetTvShowGalleryUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        getTvShowGalleryUseCase = GetTvShowGalleryUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show gallery from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowGallery(tvShowId) } returns fakeGallery

        // when
        val result = getTvShowGalleryUseCase(tvShowId)

        // Then
        assertEquals(result, fakeGallery)

    }

    @Test
    fun `should call getTvShowGallery on repository exactly once`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowGallery(tvShowId) } returns fakeGallery

        // When
        getTvShowGalleryUseCase(tvShowId)

        // Then
        coVerify(exactly = 1) { tvShowRepository.getTvShowGallery(tvShowId) }
    }

    private companion object {
        val tvShowId = 1

    }

}