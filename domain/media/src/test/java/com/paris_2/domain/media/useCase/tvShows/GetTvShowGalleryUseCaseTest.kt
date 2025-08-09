package com.paris_2.domain.media.useCase.tvShows

import com.google.common.truth.Truth.assertThat
import com.paris_2.domain.media.repository.TvShowRepository
import com.paris_2.domain.media.testUtils.fakeGallery
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetTvShowGalleryUseCaseTest {

    private lateinit var getTvShowGalleryUseCase: GetTvShowGalleryUseCase
    private val tvShowRepository: TvShowRepository = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        getTvShowGalleryUseCase = GetTvShowGalleryUseCase(tvShowRepository)
    }

    @Test
    fun `should return tv show gallery from repository`() = runTest {
        // Given
        coEvery { tvShowRepository.getTvShowGallery(tvShowId) } returns fakeGallery

        // When
        val result = getTvShowGalleryUseCase(tvShowId)

        // Then
        assertThat(result).isEqualTo(fakeGallery)
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
        const val tvShowId = 1
    }
}
