package com.domain.mediaDetails.useCases.tvShows

import com.domain.mediaDetails.repository.TvShowRepository
import io.mockk.coEvery
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
        val tvShowId = 1

        // when
        coEvery { tvShowRepository.getTvShowGallery(tvShowId) } returns fakeGallery

        // Then
        val result = getTvShowGalleryUseCase(tvShowId)
        assertEquals(result, fakeGallery)

    }

}